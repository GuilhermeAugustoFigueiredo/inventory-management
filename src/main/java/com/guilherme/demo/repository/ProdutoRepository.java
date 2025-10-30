package com.guilherme.demo.repository;

import com.guilherme.demo.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByMarcaContainsIgnoreCase(String marca);
    List<Produto> findByCategoriaContainsIgnoreCase(String categoria);
    Produto findByNomeContainsIgnoreCase(String nome);
}
