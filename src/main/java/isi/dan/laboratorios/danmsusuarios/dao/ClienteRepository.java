package isi.dan.laboratorios.danmsusuarios.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isi.dan.laboratorios.danmsusuarios.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByIdAndFechaBajaIsNull(Integer id);
}
