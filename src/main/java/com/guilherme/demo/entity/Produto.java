package com.guilherme.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    private Long id;
    private String categoria;
    private String marca;
    private String nome;
    private Double precoUnitario;
    private Double precoFinal;
}
