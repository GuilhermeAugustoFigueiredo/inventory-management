package com.guilherme.demo.dto.UsuarioDto;

import com.guilherme.demo.entity.Usuario;

import java.util.List;

public class UsuarioMapper {
    public static UsuarioListagemDto toListagemDto(Usuario entity) {
        if (entity == null) {
            return null;
        }

        return new UsuarioListagemDto(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getCargo(),
                entity.getSalario(),
                entity.getAtivo()
        );
    }

    public static List<UsuarioListagemDto> toListagemDtos(List<Usuario> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(UsuarioMapper::toListagemDto)
                .toList();
    }

    public static Usuario toEntity(UsuarioCadastroDto dto) {
        if (dto == null) {
            return null;
        }

        return new Usuario(
                null,
                dto.getNome(),
                dto.getCpf(),
                dto.getCargo(),
                dto.getSalario(),
                dto.getAtivo()
        );
    }

    public static Usuario toEntity(UsuarioCadastroDto dto, Long id) {
        if (dto == null) {
            return null;
        }

        return new Usuario(
                id,
                dto.getNome(),
                dto.getCpf(),
                dto.getCargo(),
                dto.getSalario(),
                dto.getAtivo()
        );
    }
}
