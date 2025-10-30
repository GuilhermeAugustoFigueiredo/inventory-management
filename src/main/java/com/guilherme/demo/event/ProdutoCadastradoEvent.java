package com.guilherme.demo.event;

import com.guilherme.demo.dto.ProdutoDto.ProdutoRequestDto;
import com.guilherme.demo.entity.Produto;

public class ProdutoCadastradoEvent {
    private final ProdutoRequestDto produto;

    public ProdutoCadastradoEvent(ProdutoRequestDto produto) {
        this.produto = produto;
    }

    public ProdutoRequestDto getProduto() {
        return produto;
    }
}
