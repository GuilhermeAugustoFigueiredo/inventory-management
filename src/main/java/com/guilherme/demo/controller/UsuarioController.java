package com.guilherme.demo.controller;

import com.guilherme.demo.dto.UsuarioDto.UsuarioAtualizacaoDto;
import com.guilherme.demo.dto.UsuarioDto.UsuarioCadastroDto;
import com.guilherme.demo.dto.UsuarioDto.UsuarioListagemDto;
import com.guilherme.demo.dto.UsuarioDto.UsuarioMapper;
import com.guilherme.demo.entity.Usuario;
import com.guilherme.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "List all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioListagemDto>> listar(){
        List<Usuario> usuarios = usuarioService.listar();
        if (usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        List<UsuarioListagemDto> usuariosResponse = UsuarioMapper.toListagemDtos(usuarios);
        return ResponseEntity.status(200).body(usuariosResponse);
    }

    @Operation(summary = "List users by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> buscarPorId(@PathVariable Long id){
        Usuario usuarioFound = usuarioService.buscarPorId(id);
        var usuarioFoundResponse = UsuarioMapper.toListagemDto(usuarioFound);
        return ResponseEntity.status(200).body(usuarioFoundResponse);
    }

    @Operation(summary = "List users by their role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping("/cargo")
    public ResponseEntity<List<UsuarioListagemDto>> buscarPorCargo(@RequestParam String cargo){
        List<Usuario> usuariosFound = usuarioService.buscarPorCargo(cargo);
        var usuariosFoundResponse = UsuarioMapper.toListagemDtos(usuariosFound);
        return ResponseEntity.status(200).body(usuariosFoundResponse);
    }

    @Operation(summary = "Register a new user",
            description = "This endpoint creates a new user in the system based on the data provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioListagemDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User with this email already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UsuarioListagemDto> cadastrar(@Valid @RequestBody UsuarioCadastroDto usuarioRequest){
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest);
        var usuarioRegister = usuarioService.cadastrar(usuario);
        UsuarioListagemDto usuarioResponse = UsuarioMapper.toListagemDto(usuarioRegister);
        return ResponseEntity.status(201).body(usuarioResponse);
    }

    @Operation(summary = "Updates an existing user",
            description = "Updates the data of a user based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioListagemDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioListagemDto> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioAtualizacaoDto usuarioRequest) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioRequest, id);
        Usuario usuarioUpdated = usuarioService.atualizar(usuario);
        UsuarioListagemDto usuarioResponse = UsuarioMapper.toListagemDto(usuarioUpdated);
        return ResponseEntity.status(200).body(usuarioResponse);
    }

    @Operation(summary = "Removes a user by ID",
            description = "Permanently deletes a user from the system based on their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User removed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        usuarioService.removerPorId(id);
        return ResponseEntity.status(204).build();
    }
}
