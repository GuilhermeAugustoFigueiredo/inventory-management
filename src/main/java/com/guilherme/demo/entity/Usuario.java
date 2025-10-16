package com.guilherme.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Long id;
    private String nome;
    private String cpf;
    private String cargo;
    private Double salario;
    private Boolean ativo;
}
