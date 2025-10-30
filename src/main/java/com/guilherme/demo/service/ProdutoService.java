package com.guilherme.demo.service;

import com.guilherme.demo.entity.Produto;
import com.guilherme.demo.event.ProdutoCadastradoEvent;
import com.guilherme.demo.exception.EntidadeNaoEncontradaException;
import com.guilherme.demo.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UsuarioService usuarioService;

    public Produto cadastrar(Produto produto) {
        System.out.println("Produto " + produto.getNome() + " salvo!");
        Produto salvo = produtoRepository.save(produto);
        eventPublisher.publishEvent(new ProdutoCadastradoEvent(salvo));
        return salvo;
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

    public List<Produto> buscarPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoriaContainsIgnoreCase(categoria);
        if (produtos.isEmpty())
            throw new EntidadeNaoEncontradaException("Nenhum produto encontrado para a categoria '" + categoria + "'.");
        return produtos;
    }

    public List<Produto> buscarPorMarca(String marca) {
        List<Produto> produtos = produtoRepository.findByMarcaContainsIgnoreCase(marca);
        if (produtos.isEmpty())
            return null;
        return produtos;
    }


    public Produto buscarPorNome(String nome) {
        Produto produto = produtoRepository.findByNomeContainsIgnoreCase(nome);
        if (produto == null) {
            throw new EntidadeNaoEncontradaException("Produto com nome '" + nome + "' não encontrado.");
        }
        return produto;
    }

}
