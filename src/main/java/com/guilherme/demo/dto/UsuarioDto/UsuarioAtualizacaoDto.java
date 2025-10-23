package com.guilherme.demo.dto.UsuarioDto;

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
public class UsuarioAtualizacaoDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;
    @NotBlank
    private String cargo;
    @NotBlank
    private String email;
    @NotNull
    @Positive
    private Double salario;
    @NotNull
    private Boolean ativo;
}
