package com.guilherme.demo.service;

import com.guilherme.demo.dto.UsuarioDto.UsuarioMapper;
import com.guilherme.demo.dto.UsuarioDto.UsuarioRequestDto;
import com.guilherme.demo.dto.UsuarioDto.UsuarioResponseDto;
import com.guilherme.demo.entity.Produto;
import com.guilherme.demo.entity.Usuario;
import com.guilherme.demo.event.ProdutoCadastradoEvent;
import com.guilherme.demo.exception.ConflictException;
import com.guilherme.demo.exception.DataNotFoundException;
import com.guilherme.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void enviarNotificacao(List<Usuario> gerentes, Produto produto){
        for (Usuario u : gerentes) {
            System.out.println("Enviando email para " + u.getEmail() + "cadastro do produto " + produto.getNome() + " com sucesso");
        }
    }

    public void handleProdutoCadastrado(ProdutoCadastradoEvent event){
        Produto produto = event.getProduto();
        List<Usuario> gerentes = usuarioRepository.findByCargoContainsIgnoreCase("Gerente");
        enviarNotificacao(gerentes, produto);
    }

    public UsuarioResponseDto cadastrar(UsuarioRequestDto usuario){
        if(usuarioRepository.existsByCpf((usuario.getCpf()))){
            throw new ConflictException("O CPF do Usuário informado já foi cadastrado", "Usuários");}
        Usuario usuarioSave = usuarioRepository.save(UsuarioMapper.toEntity(usuario));
        return UsuarioMapper.toResponseDto(usuarioSave);
    }

    public UsuarioResponseDto buscarPorId(Long id){
        Usuario usuarioFound = usuarioRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Não existe usuário com esse id", "Usuários"));
        return UsuarioMapper.toResponseDto(usuarioFound);
    }

    public List<UsuarioResponseDto> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty())
            throw new DataNotFoundException("Não existem usuários", "Usuários");
        return UsuarioMapper.toResponseDtos(usuarios);
    }

    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto usuarioUpdate){
        Usuario usuarioFound = usuarioRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Não existe usuário com esse id", "Usuários"));

        usuarioFound.setNome(usuarioUpdate.getNome());
        usuarioFound.setCpf(usuarioUpdate.getCpf());
        usuarioFound.setEmail(usuarioUpdate.getEmail());
        usuarioFound.setCargo(usuarioUpdate.getCargo());
        usuarioFound.setSalario(usuarioUpdate.getSalario());
        usuarioFound.setAtivo(usuarioUpdate.getAtivo());

        usuarioRepository.save(usuarioFound);

        return UsuarioMapper.toResponseDto(usuarioFound);
    }


    public void removerPorId(Long id){
        if (usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
        }else {
            throw new DataNotFoundException("Não existe usuário com esse id", "Usuários");
        }
    }

    public List<UsuarioResponseDto> buscarPorCargo(String cargo){
        List<Usuario> usuarios = usuarioRepository.findByCargoContainsIgnoreCase(cargo);
        if (usuarios.isEmpty())
            return null;
        return UsuarioMapper.toResponseDtos(usuarios);
    }

    public UsuarioResponseDto buscarPorNome(String nome){
        Usuario usuario = usuarioRepository.findByNomeContainsIgnoreCase(nome);
        return UsuarioMapper.toResponseDto(usuario);
    }

    public UsuarioResponseDto buscarPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmailContainsIgnoreCase(email);
        return UsuarioMapper.toResponseDto(usuario);
    }

    public UsuarioResponseDto buscarPorCpf(String cpf){
        Usuario usuario = usuarioRepository.findByCpfContainsIgnoreCase(cpf);
        return UsuarioMapper.toResponseDto(usuario);
    }

    public List<UsuarioResponseDto> buscarPorAtivo(Boolean ativo){
        List<Usuario> usuarios = usuarioRepository.findByAtivo(ativo);
        return UsuarioMapper.toResponseDtos(usuarios);
    }

    public void adicionarFoto(Long idUsuario, byte[] novaFoto) {
        if (!usuarioRepository.existsById(idUsuario))
            throw new DataNotFoundException("Não existe um produto com esse ID", "Produtos");
        usuarioRepository.setFoto(idUsuario, novaFoto);
    }
}
