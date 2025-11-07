package com.guilherme.demo.dto.UsuarioDto;

import com.guilherme.demo.entity.Usuario;

import java.util.List;

public class UsuarioMapper {
    public static UsuarioResponseDto toResponseDto(Usuario entity) {
        if (entity == null) {
            return null;
        }

        UsuarioResponseDto responseDto = new UsuarioResponseDto();

        responseDto.setId(entity.getId());
        responseDto.setNome(entity.getNome());
        responseDto.setCpf(entity.getCpf());
        responseDto.setEmail(entity.getEmail());
        responseDto.setCargo(entity.getCargo());
        responseDto.setSalario(entity.getSalario());
        responseDto.setAtivo(entity.getAtivo());
        responseDto.setFoto(entity.getFoto());

        return responseDto;
    }

    public static List<UsuarioResponseDto> toResponseDtos(List<Usuario> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(UsuarioMapper::toResponseDto)
                .toList();
    }

    public static Usuario toEntity(UsuarioRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setCargo(dto.getCargo());
        usuario.setSalario(dto.getSalario());
        usuario.setAtivo(dto.getAtivo());

        return usuario;
    }
}
