package com.guilherme.demo.service;

import com.guilherme.demo.entity.Produto;
import com.guilherme.demo.entity.Usuario;
import com.guilherme.demo.event.ProdutoCadastradoEvent;
import com.guilherme.demo.exception.EntidadeNaoEncontradaException;
import com.guilherme.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void enviarNotificacao(List<Usuario> gerentes, Produto produto){
        for (Usuario u : gerentes) {
            System.out.println("Enviando email para " + u.getEmail());
        }
    }

    public Usuario cadastrar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Usuario de id %d nao encontrado".formatted(id))));
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizar(Usuario usuario){
        if (usuarioRepository.existsById(usuario.getId())){
            usuario.setId(usuario.getId());
            return usuarioRepository.save(usuario);
        }else {
            throw new EntidadeNaoEncontradaException(String.format("Usuario de id %d nao encontrado".formatted(usuario.getId())));
        }
    }

    public void removerPorId(Long id){
        if (usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }else {
            throw new EntidadeNaoEncontradaException(String.format("Usuario de id %d nao encontrado".formatted(id)));
        }
    }

    public List<Usuario> buscarPorCargo(String cargo){
        List<Usuario> usuarios = usuarioRepository.findByCargoIgnoreCase(cargo);
        if (usuarios.isEmpty())
            return null;

        return usuarios;
    }

}
