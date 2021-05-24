package isi.dan.laboratorios.danmsusuarios.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;
import isi.dan.laboratorios.danmsusuarios.exception.RecursoNoEncontradoException;
import isi.dan.laboratorios.danmsusuarios.exception.RiesgoException;
import isi.dan.laboratorios.danmsusuarios.dao.ClienteRepository;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    RiesgoService riesgoService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Cliente darAlta(Cliente cliente) throws RiesgoException {

        if (cliente.getHabilitadoOnline() != null && cliente.getHabilitadoOnline()) {
            Boolean situacionCrediticiaPositiva = riesgoService.reporteBCRAPositivo(cliente.getCuit());

            if (situacionCrediticiaPositiva) {
                return clienteRepository.save(cliente);
            } else
                throw new RiesgoException("BCRA");
        } else {
            return clienteRepository.save(cliente);
        }
    }

    @Override
    public void darBaja(Integer id) throws RecursoNoEncontradoException {
        
            Optional<Cliente> response = clienteRepository.findById(id);

            if(response.isEmpty()) throw new RecursoNoEncontradoException();

            Cliente cliente = response.get();

            if (!pedidoService.pedidosDelCliente(cliente).isEmpty()) {
                cliente.setFechaBaja(LocalDateTime.now());
                clienteRepository.save(cliente);
            } else {
                clienteRepository.delete(cliente);
            }
        
    }

    @Override
    public List<Cliente> buscar(String cuit, String razon) {
        Iterable<Cliente> temp = clienteRepository.findAll();

        List<Cliente> clientes = new ArrayList<>();

        temp.forEach(clientes::add);

        clientes.removeIf(cliente -> cliente.getFechaBaja() != null);

        if (cuit != null) {
            clientes.removeIf(cliente -> !cuit.equals(cliente.getCuit()));
        }

        if (razon != null) {
            clientes.removeIf(cliente -> !razon.equals(cliente.getRazonSocial()));
        }

        return clientes;
    }

    @Override
    public Cliente buscarPorId(Integer id) throws RecursoNoEncontradoException {
        Optional<Cliente> cliente = clienteRepository.findByIdAndFechaBajaIsNull(id);

        if (cliente.isPresent())
            return cliente.get();
        throw new RecursoNoEncontradoException();
    }

    @Override
    public Cliente actualizar(Cliente nuevo) throws RecursoNoEncontradoException {
        Optional<Cliente> cliente = clienteRepository.findByIdAndFechaBajaIsNull(nuevo.getId());

        if (cliente.isPresent()) {
            return clienteRepository.save(nuevo);
        }
        throw new RecursoNoEncontradoException();
    }

}
