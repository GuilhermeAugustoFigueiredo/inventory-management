package com.guilherme.demo.service;

import com.guilherme.demo.dto.ProdutoDto.ProdutoRequestDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoResponseDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoMapper;
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

    public ProdutoResponseDto cadastrar(ProdutoRequestDto produto) {
        System.out.println("Produto " + produto.getNome() + " salvo!");
        var evento = new ProdutoCadastradoEvent(produto);
        eventPublisher.publishEvent(evento);
        usuarioService.handleProdutoCadastrado(evento);

        Produto produtoSalvo = produtoRepository.save(ProdutoMapper.toEntity(produto));
        return ProdutoMapper.toResponseDto(produtoSalvo);
    }

    public ProdutoResponseDto buscarPorId(Long id) {
        Produto produtoFound = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Nenhum produto encontrado com o id: " + id));
        return ProdutoMapper.toResponseDto(produtoFound);
    }

    public List<ProdutoResponseDto> listar() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty())
            throw new EntidadeNaoEncontradaException("Nenhum produto encontrado");
        return ProdutoMapper.toResponseDtos(produtos);
    }

    public ProdutoResponseDto atualizar(Long id, ProdutoRequestDto produto) {
        Produto produtoUpdate = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Nenhum produto encontrado com o id: " + id));

        produtoUpdate.setCategoria(produto.getCategoria());
        produtoUpdate.setNome(produto.getNome());
        produtoUpdate.setMarca(produto.getMarca());
        produtoUpdate.setPrecoFinal(produto.getPrecoFinal());
        produtoUpdate.setPrecoFinal(produto.getPrecoFinal());

        Produto produtoSave = produtoRepository.save(produtoUpdate);

        return ProdutoMapper.toResponseDto(produtoSave);
    }

    public void removerPorId(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new EntidadeNaoEncontradaException("Nenhum produto encontrado com o id: " + id);
        }
    }

    public List<ProdutoResponseDto> buscarPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
        if (produtos.isEmpty())
            throw new EntidadeNaoEncontradaException("Nenhum produto encontrado com a categoria: " + categoria);
        return ProdutoMapper.toResponseDtos(produtos);
    }

    public List<ProdutoResponseDto> buscarPorMarca(String marca) {
        List<Produto> produtos = produtoRepository.findByMarcaContainingIgnoreCase(marca);
        if (produtos.isEmpty())
            throw new EntidadeNaoEncontradaException("Nenhum produto encontrado com a marca: " + marca);
        return ProdutoMapper.toResponseDtos(produtos);
    }

    public ProdutoResponseDto buscarPorNome(String nome) {
        var produtoFound = produtoRepository.findByNomeContainingIgnoreCase(nome);
        if (produtoFound == null)
            throw new EntidadeNaoEncontradaException("Nenhum produto encontrado com o nome: " + nome);
        return ProdutoMapper.toResponseDto(produtoFound);
    }
}
