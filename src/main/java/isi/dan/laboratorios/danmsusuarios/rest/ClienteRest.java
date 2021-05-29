package isi.dan.laboratorios.danmsusuarios.rest;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;
import isi.dan.laboratorios.danmsusuarios.domain.Obra;
import isi.dan.laboratorios.danmsusuarios.exception.RecursoNoEncontradoException;
import isi.dan.laboratorios.danmsusuarios.exception.RiesgoException;
import isi.dan.laboratorios.danmsusuarios.service.ClienteService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/cliente")
@Api(value = "ClienteRest", description = "Permite gestionar los clientes de la empresa")
public class ClienteRest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ObraRest obraRest;

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    public ResponseEntity<Cliente> clientePorId(@PathVariable Integer id) {
        Cliente cliente;

        try {
            cliente = clienteService.buscarPorId(id);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> clientePor(@RequestParam(required = false) String cuit,
            @RequestParam(required = false) String razon, @RequestParam(required = false) Integer obraId) {

        if(obraId != null) {
            ResponseEntity<Obra> response = obraRest.obraPorId(obraId);

            if(response.getStatusCode() == HttpStatus.OK){
                ArrayList<Cliente> cliente = new ArrayList<Cliente>();

                cliente.add(response.getBody().getCliente());

                return ResponseEntity.ok(cliente);
            }
            return ResponseEntity.notFound().build();
        } 
        return ResponseEntity.ok(clienteService.buscar(cuit, razon));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cliente nuevo) {
        System.out.println(" crear cliente " + nuevo);

        if (esClienteValido(nuevo)) {
            try {
                clienteService.darAlta(nuevo);
            } catch (RiesgoException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            return ResponseEntity.ok(nuevo);
        }
        return ResponseEntity.badRequest().body("Cliente no valido");
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza un cliente")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe") })
    public ResponseEntity<Cliente> actualizar(@RequestBody Cliente actualizado, @PathVariable Integer id) {
        actualizado.setId(id);
        try {
            return ResponseEntity.ok(clienteService.actualizar(actualizado));
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> borrar(@PathVariable Integer id) {
        try {
            clienteService.darBaja(id);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();

    }

    private boolean esClienteValido(Cliente cliente) {
        if (cliente.getObras() == null || cliente.getObras().isEmpty()) {
            return false;
        }

        for (int i = 0; i < cliente.getObras().size(); i++) {
            Obra obra = cliente.getObras().get(i);
            if (obra.getTipo() == null)
                return false;
        }

        if (cliente.getUsuario() == null || cliente.getUsuario().getUser() == null
                || cliente.getUsuario().getPassword() == null) {
            return false;
        }
        return true;
    }

}
