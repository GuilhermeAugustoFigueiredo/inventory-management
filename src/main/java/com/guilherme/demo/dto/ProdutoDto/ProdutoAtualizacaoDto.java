package com.guilherme.demo.dto.ProdutoDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoAtualizacaoDto {
    @NotBlank
    private String categoria;
    @NotBlank
    private String marca;
    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private Double precoUnitario;
    @NotNull
    @Positive
    private Double precoFinal;
}
