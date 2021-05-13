package isi.dan.laboratorios.danmsusuarios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;
import isi.dan.laboratorios.danmsusuarios.domain.Pedido;
import isi.dan.laboratorios.danmsusuarios.exception.RecursoNoEncontradoException;
import isi.dan.laboratorios.danmsusuarios.exception.RiesgoException;
import isi.dan.laboratorios.danmsusuarios.repository.ClienteRepository;

@SpringBootTest
public class ClienteServiceImpUT {

    @Autowired
    ClienteService clienteService;

    @MockBean
    RiesgoService riesgoService;

    @MockBean
    PedidoService pedidoService;

    @MockBean
    ClienteRepository clienteRepository;

    List<Cliente> clientes;
    List<Pedido> pedidos;

    Cliente clienteAlta;
    Cliente clienteAltaRiesgo;
    Cliente clienteActualizar;

    @BeforeEach
    void setUp() {
        clientes = new ArrayList<Cliente>();

        Cliente c1 = new Cliente();
        c1.setCuit("2099988871");

        Cliente c2 = new Cliente();
        c2.setRazonSocial("Cliente 02");

        Cliente c3 = new Cliente();
        c3.setFechaBaja(new Date());

        clientes.add(c1);
        clientes.add(c2);
        clientes.add(c3);

        pedidos = new ArrayList<>();
        pedidos.add(new Pedido());

        clienteAlta = new Cliente();
        clienteAlta.setCuit("1");
        clienteAlta.setHabilitadoOnline(true);

        clienteAltaRiesgo = new Cliente();
        clienteAltaRiesgo.setHabilitadoOnline(true);
        clienteAltaRiesgo.setCuit("2");

        clienteActualizar = new Cliente();
        clienteActualizar.setId(4);
    }

    @Test
    void testAltaConRiesgo() {
        when(riesgoService.reporteBCRAPositivo(clienteAltaRiesgo.getCuit())).thenReturn(false);

        assertThrows(RiesgoException.class, () -> clienteService.darAlta(clienteAltaRiesgo));
    }

    @Test
    void testAltaSinRiesgo() throws RiesgoException {
        when(riesgoService.reporteBCRAPositivo(clienteAlta.getCuit())).thenReturn(true);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAlta);

        assertNotNull(clienteService.darAlta(clienteAlta));
    }

    @Test
    void testBajaConPedidos() throws RecursoNoEncontradoException {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
        when(pedidoService.pedidosDelCliente(any(Cliente.class))).thenReturn(pedidos);

        clienteService.darBaja(1);

        verify(clienteRepository, times(1)).save(argThat((cliente) -> cliente.getFechaBaja() != null));
    }

    @Test
    void testBuscarPorCuit() {
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> respuesta = clienteService.buscar("2099988871", null);

        assertEquals(1, respuesta.size());
        assertEquals(clientes.get(0), respuesta.get(0));
    }

    @Test
    void testBuscarPorRazon() {
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> respuesta = clienteService.buscar(null, "Cliente 02");

        assertEquals(1, respuesta.size());
        assertEquals(clientes.get(1), respuesta.get(0));
    }

    @Test
    void testBuscarTodos() {
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> respuesta = clienteService.buscar(null, null);
        assertEquals(2, respuesta.size());
    }

    @Test
    void testBuscarPorId() throws RecursoNoEncontradoException {
        when(clienteRepository.findById(34)).thenReturn(Optional.of(new Cliente()));

        Cliente cliente = clienteService.buscarPorId(34);
        assertNotNull(cliente);
    }

    @Test
    void testIdNoEncontrado() {
        when(clienteRepository.findById(3)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> clienteService.buscarPorId(3));
    }

    @Test
    void testActualizar() throws RecursoNoEncontradoException {
        when(clienteRepository.findById(clienteActualizar.getId())).thenReturn(Optional.of(clienteActualizar));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizar);
        
        assertNotNull(clienteService.actualizar(clienteActualizar));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}
