package com.guilherme.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guilherme.demo.dto.UsuarioDto.UsuarioRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String cargo;
    private Double salario;
    private Boolean ativo;
    @JsonIgnore
    @Column(length = 50 * 1024 * 1024) // 50 Mega Bytes
    private byte[] foto;

    public Usuario(UsuarioRequestDto dto) {
        this.nome = dto.getNome();
        this.email = dto.getEmail();
        this.cpf = dto.getCpf();
        this.cargo = dto.getCargo();
        this.salario = dto.getSalario();
        this.ativo = dto.getAtivo();
    }
}
