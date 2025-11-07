package com.guilherme.demo.event.handler;

import com.guilherme.demo.dto.UsuarioDto.UsuarioResponseDto;
import com.guilherme.demo.entity.Produto;
import com.guilherme.demo.entity.Usuario;
import com.guilherme.demo.event.ProdutoCadastradoEvent;
import com.guilherme.demo.service.EmailService;
import com.guilherme.demo.service.UsuarioService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoCadastradoHandler {

    private final EmailService emailService;
    private final UsuarioService usuarioService;

    public ProdutoCadastradoHandler(EmailService emailService, UsuarioService usuarioService) {
        this.emailService = emailService;
        this.usuarioService = usuarioService;
    }

    @EventListener
    public void handle(ProdutoCadastradoEvent event) {
        Produto produto = event.getProduto();

        System.out.println("[LOG] Produto cadastrado: " + produto.getNome() + " (ID: " + produto.getId() + ")");

        List<UsuarioResponseDto> gerentes = usuarioService.buscarPorCargo("Gerente");

        for (UsuarioResponseDto gerente : gerentes) {
            emailService.enviarEmail(
                    gerente.getEmail(),
                    "Novo produto cadastrado",
                    "Olá " + gerente.getNome() + ", o produto '" + produto.getNome() + "' foi cadastrado com sucesso."
            );
        }
    }
}