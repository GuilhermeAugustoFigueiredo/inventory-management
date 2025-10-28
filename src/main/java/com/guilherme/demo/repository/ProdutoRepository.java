package com.guilherme.demo.repository;

import com.guilherme.demo.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaContainingIgnoreCase(String categoria);
    List<Produto> findByMarcaContainingIgnoreCase(String marca);
    Produto findByNomeContainingIgnoreCase(String nome);
}
