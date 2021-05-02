package isi.dan.laboratorios.danmsusuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;
import isi.dan.laboratorios.danmsusuarios.domain.Obra;
import isi.dan.laboratorios.danmsusuarios.domain.TipoObra;
import isi.dan.laboratorios.danmsusuarios.domain.Usuario;
import isi.dan.laboratorios.danmsusuarios.exception.RecursoNoEncontradoException;
import isi.dan.laboratorios.danmsusuarios.exception.RiesgoException;
import isi.dan.laboratorios.danmsusuarios.rest.ClienteRest;
import isi.dan.laboratorios.danmsusuarios.service.ClienteService;

@SpringBootTest
public class ClienteRestUT {

    @Autowired
    ClienteRest clienteRest;

    @MockBean
    ClienteService clienteService;

    List<Cliente> clientes;

    Cliente clienteValido;

    Cliente clienteSinObra;

    Cliente clienteSinTipoObra;

    Cliente clienteSinInformacionUsuario;

    Cliente clienteSinContrasenia;

    Cliente clienteRiesgo;

    Cliente clienteActualizado;

    @BeforeEach
    void setUp() {
        clienteValido = new Cliente();

        // Obras validas
        List<Obra> obrasValidas = new ArrayList<>();
        Obra obra = new Obra();
        obra.setTipo(new TipoObra());
        obrasValidas.add(obra);
        clienteValido.setObras(obrasValidas);

        // Usuario valido
        Usuario usuario = new Usuario();
        usuario.setUser("12");
        usuario.setPassword("1234");
        clienteValido.setUsuario(usuario);

        clienteSinObra = new Cliente();

        // Cliente sin tipo obra
        clienteSinTipoObra = new Cliente();
        List<Obra> obrasSinTipo = new ArrayList<>();
        obrasSinTipo.add(new Obra());
        clienteSinTipoObra.setObras(obrasSinTipo);

        // Cliente sin iformacion
        clienteSinInformacionUsuario = new Cliente();
        clienteSinInformacionUsuario.setObras(obrasValidas);

        // CLiente sin contreasenia
        clienteSinContrasenia = new Cliente();
        clienteSinContrasenia.setObras(obrasValidas);
        clienteSinContrasenia.setUsuario(new Usuario());

        clienteRiesgo = new Cliente();

        clienteActualizado = new Cliente();

        clientes = new ArrayList<>();
    }

    @Test
    void todosTest() {
        when(clienteService.buscar(null, null)).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clienteRest.todos(null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clientes, response.getBody());
    }

    @Test
    void crearClienteValidoTest() throws RiesgoException {
        when(clienteService.darAlta(clienteValido)).thenReturn(clienteValido);

        ResponseEntity<?> response = clienteRest.crear(clienteValido);
        assertEquals(clienteValido, response.getBody());
        verify(clienteService, times(1)).darAlta(clienteValido);
    }

    @Test
    void crearClienteSinObraTest() {
        ResponseEntity<?> response = clienteRest.crear(clienteSinObra);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cliente no valido", response.getBody());
    }

    @Test
    void crearClienteConObraSinTipoTest() {
        ResponseEntity<?> response = clienteRest.crear(clienteSinTipoObra);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cliente no valido", response.getBody());
    }

    @Test
    void crearClienteSinInformacionDeUsuario() {
        ResponseEntity<?> response = clienteRest.crear(clienteSinInformacionUsuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cliente no valido", response.getBody());
    }

    @Test
    void crearClienteSinClave() {
        ResponseEntity<?> response = clienteRest.crear(clienteSinContrasenia);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cliente no valido", response.getBody());
    }

    @Test
    void crearConRiesgoTest() throws RiesgoException {
        when(clienteService.darAlta(clienteRiesgo)).thenThrow(RiesgoException.class);

        assertEquals(HttpStatus.BAD_REQUEST, clienteRest.crear(clienteRiesgo).getStatusCode());
    }

    @Test
    void actualizarEncontradoTest() throws RecursoNoEncontradoException {
        when(clienteService.actualizar(clienteActualizado)).thenReturn(clienteActualizado);

        ResponseEntity<Cliente> response = clienteRest.actualizar(clienteActualizado, 3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteActualizado, response.getBody());
        verify(clienteService, times(1)).actualizar(clienteActualizado);
    }

    @Test
    void actualizarNoEncontradoTest() throws RecursoNoEncontradoException {
        when(clienteService.actualizar(clienteActualizado)).thenThrow(RecursoNoEncontradoException.class);

        ResponseEntity<Cliente> response = clienteRest.actualizar(clienteActualizado, 4);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void borrarEncontrado() throws RecursoNoEncontradoException {
        assertEquals(HttpStatus.OK, clienteRest.borrar(1).getStatusCode());
        verify(clienteService, times(1)).darBaja(1);
    }

    @Test
    void borrarNoEncontradoTest() throws RecursoNoEncontradoException {
        doThrow(new RecursoNoEncontradoException()).when(clienteService).darBaja(5);

        assertEquals(HttpStatus.NOT_FOUND, clienteRest.borrar(5).getStatusCode());
        verify(clienteService, times(1)).darBaja(5);
    }
}
