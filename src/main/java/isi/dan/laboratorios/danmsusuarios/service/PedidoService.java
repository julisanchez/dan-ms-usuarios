package isi.dan.laboratorios.danmsusuarios.service;

import java.util.List;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;
import isi.dan.laboratorios.danmsusuarios.domain.Pedido;

public interface PedidoService {
    public List<Pedido> pedidosDelCliente(Cliente cliente);
}
