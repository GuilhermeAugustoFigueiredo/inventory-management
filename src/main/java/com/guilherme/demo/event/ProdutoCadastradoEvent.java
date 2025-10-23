package com.guilherme.demo.event;

import com.guilherme.demo.entity.Produto;

public class ProdutoCadastradoEvent {
    private final Produto produto;

    public ProdutoCadastradoEvent(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }
}
