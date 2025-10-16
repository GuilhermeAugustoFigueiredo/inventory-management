package com.guilherme.demo.dto.UsuarioDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioListagemDto {
    private Long id;
    private String nome;
    private String cpf;
    private String cargo;
    private Double salario;
    private Boolean ativo;
}
