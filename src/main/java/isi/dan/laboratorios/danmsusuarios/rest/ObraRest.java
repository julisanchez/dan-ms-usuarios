package isi.dan.laboratorios.danmsusuarios.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.annotations.ApiResponses;
import isi.dan.laboratorios.danmsusuarios.domain.Obra;
import io.swagger.annotations.ApiResponse;

@RestController
@CrossOrigin
@RequestMapping("/api/obra")
@Api(value = "ObraRest", description = "Permite gestionar las obras de la empresa")
public class ObraRest {

    private static final List<Obra> listaObras = new ArrayList<>();
    private static Integer ID_GEN = 1;

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca una obra por id")
    public ResponseEntity<Obra> obraPorId(@PathVariable Integer id) {

        Optional<Obra> c = listaObras.stream().filter(unaObr -> unaObr.getId().equals(id)).findFirst();
        return ResponseEntity.of(c);
    }

    @GetMapping
    public ResponseEntity<List<Obra>> obras(@RequestParam(required = false) Integer cliente,
            @RequestParam(required = false) Integer tipo) {

        Stream<Obra> obras = listaObras.stream();

        if (cliente != null) {
            obras = obras.filter(obra -> obra.getCliente().getId() == cliente);
        }
        if (tipo != null) {
            obras = obras.filter(obra -> obra.getTipo().getId() == tipo);
        }

        return ResponseEntity.ok(obras.collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Obra> crear(@RequestBody Obra nuevo) {
        System.out.println(" crear obra " + nuevo);
        nuevo.setId(ID_GEN++);
        listaObras.add(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Actualiza una obra")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"), @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe") })
    public ResponseEntity<Obra> actualizar(@RequestBody Obra nuevo, @PathVariable Integer id) {
        OptionalInt indexOpt = IntStream.range(0, listaObras.size()).filter(i -> listaObras.get(i).getId().equals(id))
                .findFirst();

        if (indexOpt.isPresent()) {
            listaObras.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Obra> borrar(@PathVariable Integer id) {
        OptionalInt indexOpt = IntStream.range(0, listaObras.size()).filter(i -> listaObras.get(i).getId().equals(id))
                .findFirst();

        if (indexOpt.isPresent()) {
            listaObras.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
