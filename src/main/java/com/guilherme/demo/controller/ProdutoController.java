package com.guilherme.demo.controller;

import com.guilherme.demo.dto.ProdutoDto.ProdutoRequestDto;
import com.guilherme.demo.dto.ProdutoDto.ProdutoResponseDto;
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
    public ResponseEntity<List<ProdutoResponseDto>> listar(){
        List<ProdutoResponseDto> produtos = produtoService.listar();
        if (produtos.isEmpty())
            return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(produtos);
    }

    @Operation(summary = "Finds a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found with the provided ID",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarPorId(@PathVariable Long id){
        ProdutoResponseDto produtoFound = produtoService.buscarPorId(id);
        return ResponseEntity.status(200).body(produtoFound);
    }

    @Operation(summary = "Finds a product by its name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found with the provided name",
                    content = @Content)
    })
    @GetMapping("/nome")
    public ResponseEntity<ProdutoResponseDto> buscarPorNome(@RequestParam String nome){
        var produtoFound = produtoService.buscarPorNome(nome);
        return ResponseEntity.status(200).body(produtoFound);
    }

    @Operation(summary = "Finds products by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully"),
            @ApiResponse(responseCode = "204", description = "No products found for the provided brand",
                    content = @Content)
    })
    @GetMapping("/marca")
    public ResponseEntity<List<ProdutoResponseDto>> buscarPorMarca(@RequestParam String marca){
        var produtosFound = produtoService.buscarPorMarca(marca);
        return ResponseEntity.status(200).body(produtosFound);
    }

    @Operation(summary = "Finds products by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully"),
            @ApiResponse(responseCode = "204", description = "No products found for the provided category",
                    content = @Content)
    })
    @GetMapping("/categoria")
    public ResponseEntity<List<ProdutoResponseDto>> buscarPorCategoria(@RequestParam String categoria){
        var produtosFound = produtoService.buscarPorMarca(categoria);
        return ResponseEntity.status(200).body(produtosFound);
    }

    @Operation(summary = "Registers a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product registered successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Product with this name/SKU already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDto> cadastrar(@Valid @RequestBody ProdutoRequestDto produtoRequest){
        ProdutoResponseDto produtoCadastrado = produtoService.cadastrar(produtoRequest);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    @Operation(summary = "Updates an existing product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found with the provided ID",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDto produtoRequest) {
        ProdutoResponseDto produtoAtualizado = produtoService.atualizar(id, produtoRequest);
        return ResponseEntity.status(200).body(produtoAtualizado);
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
