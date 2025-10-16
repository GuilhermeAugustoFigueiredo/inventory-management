package com.guilherme.demo.service;

import com.guilherme.demo.entity.Produto;
import com.guilherme.demo.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto cadastrar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Produto de id %d nao encontrado".formatted(id))));
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto atualizar(Produto produto) {
        if (produtoRepository.existsById(produto.getId())) {
            produto.setId(produto.getId());
            return produtoRepository.save(produto);
        } else {
            throw new EntidadeNaoEncontradaException(String.format("Produto de id %d nao encontrado".formatted(produto.getId())));
        }
    }

    public void removerPorId(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new EntidadeNaoEncontradaException(String.format("Produto de id %d nao encontrado".formatted(id)));
        }
    }

    // estou repetindo metodos, acredito que existe uma forma melhor para nao ficar repetindo codigo

    public List<Produto> buscarPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty())
            return null;

        for (Produto p : produtos) {
            if (p.getCategoria().equalsIgnoreCase(categoria))
                produtos.add(p);
        }
        return produtos;
    }

    public List<Produto> buscarPorMarca(String marca) {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty())
            return null;

        for (Produto p : produtos) {
            if (p.getMarca().equalsIgnoreCase(marca))
                produtos.add(p);
        }
        return produtos;
    }

    public Produto buscarPorNome(String nome) {
        List<Produto> produtos = produtoRepository.findAll();
        Produto produtoFound = null;

        if (produtos.isEmpty())
            return null;

        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome))
                produtoFound = p;
        }

        if (produtoFound == null)
            throw new EntidadeNaoEncontradaException(String.format("Produto de nome %s nao encontrado".formatted(nome)));

        return produtoFound;
    }
}
