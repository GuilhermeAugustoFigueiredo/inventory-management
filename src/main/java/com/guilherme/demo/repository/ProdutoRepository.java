package com.guilherme.demo.repository;

import com.guilherme.demo.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaContainingIgnoreCase(String categoria);
    List<Produto> findByMarcaContainingIgnoreCase(String marca);
    Produto findByNomeContainingIgnoreCase(String nome);
    @Modifying
    @Transactional
    @Query("update Produto p set p.foto = ?2 where p.id = ?1")
    void setFoto(Long id, byte[] foto);

    @Query("select p.foto from Produto p where p.id = ?1")
    byte[] getFoto(Long id);
}
