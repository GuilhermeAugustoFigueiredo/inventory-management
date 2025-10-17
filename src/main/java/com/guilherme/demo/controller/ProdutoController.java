package com.guilherme.demo.controller;

import com.guilherme.demo.dto.ProdutoDto.ProdutoAtualizacaoDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoCadastroDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoListagemDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoMapper;
import com.guilherme.demo.entity.Produto;
import com.guilherme.demo.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Lists all registered products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully"),
            @ApiResponse(responseCode = "204", description = "No products found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ProdutoListagemDto>> listar(){
        List<Produto> produtos = produtoService.listar();

        if (produtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<ProdutoListagemDto> produtosResponse = ProdutoMapper.toListagemDtos(produtos);

        return ResponseEntity.status(200).body(produtosResponse);
    }

    @Operation(summary = "Finds a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found with the provided ID",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoListagemDto> buscarPorId(@PathVariable Long id){
        Produto produtoFound = produtoService.buscarPorId(id);
        var produtoFoundResponse = ProdutoMapper.toListagemDto(produtoFound);
        return ResponseEntity.status(200).body(produtoFoundResponse);
    }

    @Operation(summary = "Finds a product by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found with the provided name",
                    content = @Content)
    })
    @GetMapping("/nome")
    public ResponseEntity<ProdutoListagemDto> buscarPorNome(@RequestParam String nome){
        var produtoFound = produtoService.buscarPorNome(nome);
        var produtoResponse = ProdutoMapper.toListagemDto(produtoFound);
        return ResponseEntity.status(200).body(produtoResponse);
    }

    @Operation(summary = "Finds products by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully"),
            @ApiResponse(responseCode = "204", description = "No products found for the provided brand",
                    content = @Content)
    })
    @GetMapping("/marca")
    public ResponseEntity<List<ProdutoListagemDto>> buscarPorMarca(@RequestParam String marca){
        var produtosFound = produtoService.buscarPorMarca(marca);
        var produtosResponse = ProdutoMapper.toListagemDtos(produtosFound);
        return ResponseEntity.status(200).body(produtosResponse);
    }

    @Operation(summary = "Finds products by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully"),
            @ApiResponse(responseCode = "204", description = "No products found for the provided category",
                    content = @Content)
    })
    @GetMapping("/categoria")
    public ResponseEntity<List<ProdutoListagemDto>> buscarPorCategoria(@RequestParam String categoria){
        var produtosFound = produtoService.buscarPorMarca(categoria);
        var produtosResponse = ProdutoMapper.toListagemDtos(produtosFound);
        return ResponseEntity.status(200).body(produtosResponse);
    }

    @Operation(summary = "Registers a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product registered successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoListagemDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Product with this name/SKU already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProdutoListagemDto> cadastrar(@Valid @RequestBody ProdutoCadastroDto produtoRequest){
        Produto produto = ProdutoMapper.toEntity(produtoRequest);
        var produtoRegister = produtoService.cadastrar(produto);
        ProdutoListagemDto produtoResponse = ProdutoMapper.toListagemDto(produtoRegister);
        return ResponseEntity.status(201).body(produtoResponse);
    }

    @Operation(summary = "Updates an existing product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoListagemDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found with the provided ID",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoListagemDto> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoAtualizacaoDto produtoRequest) {
        Produto produto = ProdutoMapper.toEntity(produtoRequest, id);
        Produto produtoUpdated = produtoService.atualizar(produto);
        ProdutoListagemDto produtoResponse = ProdutoMapper.toListagemDto(produtoUpdated);
        return ResponseEntity.status(200).body(produtoResponse);
    }

    @Operation(summary = "Removes a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product removed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found with the provided ID",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        produtoService.removerPorId(id);
        return ResponseEntity.status(204).build();
    }
}
