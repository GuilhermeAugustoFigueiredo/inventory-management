package com.guilherme.demo.dto.ProdutoDto;

import com.guilherme.demo.entity.Produto;

import java.util.List;

public class ProdutoMapper {

    public static ProdutoListagemDto toListagemDto(Produto entity) {
        if (entity == null) {
            return null;
        }

        return new ProdutoListagemDto(
                entity.getId(),
                entity.getCategoria(),
                entity.getMarca(),
                entity.getNome(),
                entity.getPrecoUnitario(),
                entity.getPrecoFinal()
        );
    }

    public static List<ProdutoListagemDto> toListagemDtos(List<Produto> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(ProdutoMapper::toListagemDto)
                .toList();
    }

    public static Produto toEntity(ProdutoCadastroDto dto) {
        if (dto == null) {
            return null;
        }

        return new Produto(
                null,
                dto.getCategoria(),
                dto.getMarca(),
                dto.getNome(),
                dto.getPrecoUnitario(),
                dto.getPrecoUnitario()
        );
    }

    public static Produto toEntity(ProdutoCadastroDto dto, Long id) {
        if (dto == null) {
            return null;
        }

        return new Produto(
                id,
                dto.getCategoria(),
                dto.getMarca(),
                dto.getNome(),
                dto.getPrecoUnitario(),
                dto.getPrecoUnitario()
        );
    }
}
