package com.guilherme.demo.controller;

import com.guilherme.demo.dto.ProdutoDto.ProdutoAtualizacaoDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoCadastroDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoListagemDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoMapper;
import com.guilherme.demo.entity.Produto;
import com.guilherme.demo.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoListagemDto>> listar(){
        List<Produto> produtos = produtoService.listar();

        if (produtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<ProdutoListagemDto> produtosResponse = ProdutoMapper.toListagemDtos(produtos);

        return ResponseEntity.status(200).body(produtosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoListagemDto> buscarPorId(@PathVariable Long id){
        Produto produtoFound = produtoService.buscarPorId(id);
        var produtoFoundResponse = ProdutoMapper.toListagemDto(produtoFound);
        return ResponseEntity.status(200).body(produtoFoundResponse);
    }

    @GetMapping("/nome")
    public ResponseEntity<ProdutoListagemDto> buscarPorNome(@RequestParam String nome){
        var produtoFound = produtoService.buscarPorNome(nome);
        var produtoResponse = ProdutoMapper.toListagemDto(produtoFound);
        return ResponseEntity.status(200).body(produtoResponse);
    }

    @GetMapping("/marca")
    public ResponseEntity<List<ProdutoListagemDto>> buscarPorMarca(@RequestParam String marca){
        var produtosFound = produtoService.buscarPorMarca(marca);
        var produtosResponse = ProdutoMapper.toListagemDtos(produtosFound);
        return ResponseEntity.status(200).body(produtosResponse);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<ProdutoListagemDto>> buscarPorCategoria(@RequestParam String categoria){
        var produtosFound = produtoService.buscarPorMarca(categoria);
        var produtosResponse = ProdutoMapper.toListagemDtos(produtosFound);
        return ResponseEntity.status(200).body(produtosResponse);
    }

    @PostMapping
    public ResponseEntity<ProdutoListagemDto> cadastrar(@Valid @RequestBody ProdutoCadastroDto produtoRequest){
        Produto produto = ProdutoMapper.toEntity(produtoRequest);
        var produtoRegister = produtoService.cadastrar(produto);
        ProdutoListagemDto produtoResponse = ProdutoMapper.toListagemDto(produtoRegister);
        return ResponseEntity.status(201).body(produtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoListagemDto> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoAtualizacaoDto produtoRequest) {
        Produto produto = ProdutoMapper.toEntity(produtoRequest, id);
        Produto produtoUpdated = produtoService.atualizar(produto);
        ProdutoListagemDto produtoResponse = ProdutoMapper.toListagemDto(produtoUpdated);
        return ResponseEntity.status(200).body(produtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        produtoService.removerPorId(id);
        return ResponseEntity.status(204).build();
    }
}
