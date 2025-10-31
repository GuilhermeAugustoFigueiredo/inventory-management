package com.guilherme.demo.service;

import com.guilherme.demo.dto.ProdutoDto.ProdutoMapper;
import com.guilherme.demo.dto.ProdutoDto.ProdutoRequestDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoResponseDto;
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
        Produto salvo = produtoRepository.save(ProdutoMapper.toEntity(produto));
        eventPublisher.publishEvent(new ProdutoCadastradoEvent(salvo));
        return ProdutoMapper.toResponseDto(salvo);
    }

    public ProdutoResponseDto buscarPorId(Long id) {

        Produto produtoFound = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Produto de id %d nao encontrado".formatted(id))));

        return ProdutoMapper.toResponseDto(produtoFound);
    }

    public List<ProdutoResponseDto> listar() {
        return ProdutoMapper.toResponseDtos(produtoRepository.findAll());
    }

    public ProdutoResponseDto atualizar(Long id,ProdutoRequestDto produtoUpdate) {
        Produto produtoFound = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto de id %d nao encontrado".formatted(id)));

        produtoFound.setCategoria(produtoUpdate.getCategoria());
        produtoFound.setNome(produtoUpdate.getNome());
        produtoFound.setMarca(produtoUpdate.getMarca());
        produtoFound.setPrecoFinal(produtoUpdate.getPrecoFinal());
        produtoFound.setPrecoUnitario(produtoUpdate.getPrecoUnitario());

        produtoRepository.save(produtoFound);

        return ProdutoMapper.toResponseDto(produtoFound);
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
            throw new EntidadeNaoEncontradaException("Nenhum produto encontrado para a categoria '" + categoria + "'.");
        return ProdutoMapper.toResponseDtos(produtos);
    }

    public List<ProdutoResponseDto> buscarPorMarca(String marca) {
        List<Produto> produtos = produtoRepository.findByMarcaContainingIgnoreCase(marca);
        if (produtos.isEmpty())
            return null;
        return ProdutoMapper.toResponseDtos(produtos);
    }


    public ProdutoResponseDto buscarPorNome(String nome) {
        Produto produto = produtoRepository.findByNomeContainingIgnoreCase(nome);
        if (produto == null) {
            throw new EntidadeNaoEncontradaException("Produto com nome '" + nome + "' não encontrado.");
        }
        return ProdutoMapper.toResponseDto(produto);
    }

}
