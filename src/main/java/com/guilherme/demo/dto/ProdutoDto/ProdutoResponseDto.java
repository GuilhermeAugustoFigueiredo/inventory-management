package com.guilherme.demo.dto.ProdutoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDto {
    private Long id;
    private String categoria;
    private String marca;
    private String nome;
    private Double precoUnitario;
    private Double precoFinal;
    private byte[] foto;
}
