package isi.dan.laboratorios.danmsusuarios.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;

import isi.dan.laboratorios.danmsusuarios.dao.ClienteRepository;
import isi.dan.laboratorios.danmsusuarios.domain.Cliente;

@SpringBootTest
@Profile("testing")
@Sql({"/cliente.sql"})
public class ClienteRepositoryUT {

    @Autowired
    ClienteRepository clienteRepository;

    @Test
    void testFindByIdAndDeleted(){
        Optional<Cliente> cliente = clienteRepository.findByIdAndFechaBajaIsNull(1);
        assertEquals(false, cliente.isPresent());
    }

    @Test
    void testFindByIdAndNotDeleted(){
        Optional<Cliente> cliente = clienteRepository.findByIdAndFechaBajaIsNull(2);
        assertEquals(true, cliente.isPresent());
    }
}
