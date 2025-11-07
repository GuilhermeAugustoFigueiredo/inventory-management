package com.guilherme.demo.controller;

import com.guilherme.demo.dto.UsuarioDto.UsuarioRequestDto;
import com.guilherme.demo.dto.UsuarioDto.UsuarioResponseDto;
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
    public ResponseEntity<List<UsuarioResponseDto>> listar(){
        List<UsuarioResponseDto> usuariosResponse = usuarioService.listar();
        if (usuariosResponse.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuariosResponse);
    }

    @Operation(summary = "List users by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id){
        return ResponseEntity.status(200).body(usuarioService.buscarPorId(id));
    }

    @Operation(summary = "List users by their role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping("/cargo")
    public ResponseEntity<List<UsuarioResponseDto>> buscarPorCargo(@RequestParam String cargo){
        List<UsuarioResponseDto> usuariosResponse = usuarioService.buscarPorCargo(cargo);
        if (usuariosResponse.isEmpty())
            return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(usuariosResponse);
    }

    @Operation(summary = "List user by their name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping("/nome")
    public ResponseEntity<UsuarioResponseDto> buscarPorNome(@RequestParam String nome){
        return ResponseEntity.status(200).body(usuarioService.buscarPorNome(nome));
    }

    @Operation(summary = "List user by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping("/email")
    public ResponseEntity<UsuarioResponseDto> buscarPorEmail(@RequestParam String email){
        return ResponseEntity.status(200).body(usuarioService.buscarPorEmail(email));
    }

    @Operation(summary = "List user by their Cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping("/cpf")
    public ResponseEntity<UsuarioResponseDto> buscarPorCpf(@RequestParam String cpf){
        return ResponseEntity.status(200).body(usuarioService.buscarPorCpf(cpf));
    }

    @Operation(summary = "List user by their Cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping("/ativo")
    public ResponseEntity<List<UsuarioResponseDto>> buscarPorAtivo(){
        return ResponseEntity.status(200).body(usuarioService.buscarPorAtivo(true));
    }

    @Operation(summary = "Register a new user",
            description = "This endpoint creates a new user in the system based on the data provided.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User with this email already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(@Valid @RequestBody UsuarioRequestDto usuarioRequest){
        return ResponseEntity.status(201).body(usuarioService.cadastrar(usuarioRequest));
    }

    @Operation(summary = "Updates an existing user",
            description = "Updates the data of a user based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto usuarioRequest) {
        return ResponseEntity.status(200).body(usuarioService.atualizar(id, usuarioRequest));
    }

    @CrossOrigin("*")
    @PatchMapping(value = "/foto/{idProduto}", consumes = "image/*")
    public ResponseEntity<Void> patchFoto(@PathVariable Long idUsuario, @RequestBody byte[] novaFoto) {
        usuarioService.adicionarFoto(idUsuario, novaFoto);
        return ResponseEntity.status(200).build();
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
