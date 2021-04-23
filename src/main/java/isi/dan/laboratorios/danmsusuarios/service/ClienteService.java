package isi.dan.laboratorios.danmsusuarios.service;

import java.util.List;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;
import isi.dan.laboratorios.danmsusuarios.exception.RecursoNoEncontradoException;
import isi.dan.laboratorios.danmsusuarios.exception.RiesgoException;

public interface ClienteService {
    public Cliente darAlta(Cliente cliente) throws RiesgoException;

    public void darBaja(Integer id) throws RecursoNoEncontradoException;

    public List<Cliente> buscar(String cuit, String razon);

    public Cliente buscarPorId(Integer id) throws RecursoNoEncontradoException;

    public Cliente actualizar(Cliente cliente) throws RecursoNoEncontradoException;
}
