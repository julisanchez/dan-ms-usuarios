package isi.dan.laboratorios.danmsusuarios.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;
import isi.dan.laboratorios.danmsusuarios.exception.RecursoNoEncontradoException;
import isi.dan.laboratorios.danmsusuarios.exception.RiesgoException;
import isi.dan.laboratorios.danmsusuarios.repository.ClienteRepository;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    RiesgoService riesgoService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Cliente darAlta(Cliente cliente) throws RiesgoException{
        

        if (cliente.getHabilitadoOnline()) {
            Boolean situacionCrediticiaPositiva = riesgoService.reporteBCRAPositivo(cliente.getCuit());

            if (situacionCrediticiaPositiva) {
                return clienteRepository.save(cliente);
            }
            throw new RiesgoException("BCRA");
        } else {
            clienteRepository.save(cliente);
        }
        return null;
    }

    @Override
    public void darBaja(Integer id) throws RecursoNoEncontradoException {
        Cliente cliente = buscarPorId(id);

        if (!pedidoService.pedidosDelCliente(cliente).isEmpty()) {
            cliente.setFechaBaja(new Date());
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
            clientes.removeIf(cliente -> cliente.getCuit() != cuit);
        }
        if (razon != null) {
            clientes.removeIf(cliente -> cliente.getRazonSocial() != razon);
        }

        return clientes;
    }

    @Override
    public Cliente buscarPorId(Integer id) throws RecursoNoEncontradoException {
        Optional<Cliente> cliente = clienteRepository.findById(id).filter(c -> c.getFechaBaja() == null);

        if (cliente.isPresent())
            return cliente.get();
        throw new RecursoNoEncontradoException();
    }

    @Override
    public Cliente actualizar(Cliente nuevo) throws RecursoNoEncontradoException {
        Optional<Cliente> cliente = clienteRepository.findById(nuevo.getId());

        if (cliente.isPresent()) {
            return clienteRepository.save(nuevo);
        }
        throw new RecursoNoEncontradoException();
    }

}
