package com.guilherme.demo.repository;

import com.guilherme.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByCargoContainsIgnoreCase(String cargo);
    Usuario findByNomeContainsIgnoreCase(String nome);
    Usuario findByEmailContainsIgnoreCase(String email);
    Usuario findByCpfContainsIgnoreCase(String cpf);
    List<Usuario> findByAtivo(Boolean ativo);
    Boolean existsByCpf(String cpf);
    @Modifying
    @Transactional
    @Query("update Usuario u set u.foto = ?2 where u.id = ?1")
    void setFoto(Long id, byte[] foto);

    @Query("select u.foto from Usuario u where u.id = ?1")
    byte[] getFoto(Long id);
}
