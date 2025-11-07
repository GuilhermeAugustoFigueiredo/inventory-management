package com.guilherme.demo.dto.ProdutoDto;

import com.guilherme.demo.entity.Produto;

import java.util.List;

public class ProdutoMapper {

    public static ProdutoResponseDto toResponseDto(Produto entity) {
        if (entity == null) {
            return null;
        }

        return new ProdutoResponseDto(
                entity.getId(),
                entity.getCategoria(),
                entity.getMarca(),
                entity.getNome(),
                entity.getPrecoUnitario(),
                entity.getPrecoFinal(),
                entity.getFoto()
        );
    }

    public static List<ProdutoResponseDto> toResponseDtos(List<Produto> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(ProdutoMapper::toResponseDto)
                .toList();
    }

    public static Produto toEntity(ProdutoRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return new Produto(
                null,
                dto.getCategoria(),
                dto.getMarca(),
                dto.getNome(),
                dto.getPrecoUnitario(),
                dto.getPrecoUnitario(),
                null
        );
    }
}
