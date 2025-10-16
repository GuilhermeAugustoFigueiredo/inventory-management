package com.guilherme.demo.controller;

import com.guilherme.demo.dto.UsuarioDto.UsuarioAtualizacaoDto;
import com.guilherme.demo.dto.UsuarioDto.UsuarioCadastroDto;
import com.guilherme.demo.dto.UsuarioDto.UsuarioListagemDto;
import com.guilherme.demo.dto.UsuarioDto.UsuarioMapper;
import com.guilherme.demo.entity.Usuario;
import com.guilherme.demo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioListagemDto>> listar(){
        List<Usuario> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<UsuarioListagemDto> usuariosResponse = UsuarioMapper.toListagemDtos(usuarios);

        return ResponseEntity.status(200).body(usuariosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> buscarPorId(@PathVariable Long id){
        Usuario usuarioFound = usuarioService.buscarPorId(id);
        var usuarioFoundResponse = UsuarioMapper.toListagemDto(usuarioFound);
        return ResponseEntity.status(200).body(usuarioFoundResponse);
    }

    @GetMapping("/cargo")
    public ResponseEntity<List<UsuarioListagemDto>> buscarPorCargo(@RequestParam String cargo){
        List<Usuario> usuariosFound = usuarioService.buscarPorCargo(cargo);
        var usuariosFoundResponse = UsuarioMapper.toListagemDtos(usuariosFound);
        return ResponseEntity.status(200).body(usuariosFoundResponse);
    }

    @PostMapping
    public ResponseEntity<UsuarioListagemDto> cadastrar(@Valid @RequestBody UsuarioCadastroDto usuarioRequest){
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest);
        var usuarioRegister = usuarioService.cadastrar(usuario);
        UsuarioListagemDto usuarioResponse = UsuarioMapper.toListagemDto(usuarioRegister);
        return ResponseEntity.status(201).body(usuarioResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioAtualizacaoDto usuarioRequest) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest, id);
        Usuario usuarioUpdated = usuarioService.atualizar(usuario);
        UsuarioListagemDto usuarioResponse = UsuarioMapper.toListagemDto(usuarioUpdated);
        return ResponseEntity.status(200).body(usuarioResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        usuarioService.removerPorId(id);
        return ResponseEntity.status(204).build();
    }
}
