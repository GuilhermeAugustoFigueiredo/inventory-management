package com.guilherme.demo.service;

import com.guilherme.demo.dto.ProdutoDto.ProdutoMapper;
import com.guilherme.demo.dto.ProdutoDto.ProdutoRequestDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoResponseDto;
import com.guilherme.demo.entity.Produto;
import com.guilherme.demo.event.ProdutoCadastradoEvent;
import com.guilherme.demo.exception.DataNotFoundException;
import com.guilherme.demo.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository  produtoRepository;
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
                .orElseThrow(() -> new DataNotFoundException("Não existe um produto com esse ID", "Produtos"));

        return ProdutoMapper.toResponseDto(produtoFound);
    }

    public List<ProdutoResponseDto> listar() {
        return ProdutoMapper.toResponseDtos(produtoRepository.findAll());
    }

    public ProdutoResponseDto atualizar(Long id,ProdutoRequestDto produtoUpdate) {
        Produto produtoFound = produtoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Não existe um produto com esse ID", "Produtos"));

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
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }
    }

    public List<ProdutoResponseDto> buscarPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
        if (produtos.isEmpty())
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
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
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        }
        return ProdutoMapper.toResponseDto(produto);
    }

    public void adicionarFoto(Long idProduto, byte[] novaFoto){
        if (!produtoRepository.existsById(idProduto))
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        produtoRepository.setFoto(idProduto, novaFoto);
    }
}
