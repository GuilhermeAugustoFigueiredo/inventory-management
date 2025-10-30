package com.guilherme.demo.repository;

import com.guilherme.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByCargoContainsIgnoreCase(String cargo);
}
