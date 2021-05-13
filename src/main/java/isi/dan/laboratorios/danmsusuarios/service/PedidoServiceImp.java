package isi.dan.laboratorios.danmsusuarios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;
import isi.dan.laboratorios.danmsusuarios.domain.Pedido;

@Service
public class PedidoServiceImp implements PedidoService {

    @Override
    public List<Pedido> pedidosDelCliente(Cliente cliente) {
        return new ArrayList<>();
    }
    
}
