package com.guilherme.demo.repository; // Certifique-se que o package está correto

import com.guilherme.demo.entity.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

// Importações estáticas do AssertJ e JUnit 5
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EntityManager entityManager;

    /**
     * Helper para criar e persistir um usuário.
     * O 'flush' força a escrita no banco antes da consulta.
     */
    private Usuario createAndPersistUser(String nome, String cpf, String cargo, boolean ativo) {
        Usuario newUser = new Usuario();
        newUser.setNome(nome);
        newUser.setCpf(cpf);
        newUser.setCargo(cargo);
        newUser.setAtivo(ativo);
        newUser.setEmail(nome.toLowerCase().replace(" ", ".") + "@test.com");
        newUser.setSalario(5000.0);

        this.entityManager.persist(newUser);
        this.entityManager.flush();
        return newUser;
    }

    // --- Testes para findByNomeIgnoreCase (Retorna Usuario ou null) ---
    @Nested
    @DisplayName("Testes para findByNomeIgnoreCase")
    class FindByNomeTests {

        @Test
        @DisplayName("Deve retornar usuário único pelo nome (exato, ignorando case)")
        void findByNomeIgnoreCase_Success() {
            Usuario user = createAndPersistUser("Guilherme", "111", "Dev", true);

            Usuario result = usuarioRepository.findByNomeIgnoreCase("guilherme");

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(user.getId());
        }

        @Test
        @DisplayName("Deve retornar null se nome não existe")
        void findByNomeIgnoreCase_NotFound() {
            Usuario result = usuarioRepository.findByNomeIgnoreCase("fantasma");
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("DEVE FALHAR: Deve lançar exceção se nomes exatos duplicados forem encontrados")
        void findByNomeIgnoreCase_ShouldFailOnExactDuplicates() {
            createAndPersistUser("Ana", "111", "Dev", true);
            createAndPersistUser("Ana", "222", "QA", true);

            assertThrows(IncorrectResultSizeDataAccessException.class, () -> {
                usuarioRepository.findByNomeIgnoreCase("ana");
            });
        }
    }

    // --- Testes para findByEmailIgnoreCase (Retorna Usuario ou null) ---
    @Nested
    @DisplayName("Testes para findByEmailIgnoreCase")
    class FindByEmailTests {
        @Test
        @DisplayName("Deve retornar usuário único pelo email")
        void findByEmailIgnoreCase_Success() {
            Usuario user = createAndPersistUser("Teste", "111", "Dev", true); // Email será "teste@test.com"

            Usuario result = usuarioRepository.findByEmailIgnoreCase("TESTE@test.com");

            assertThat(result).isNotNull();
            assertThat(result.getEmail()).isEqualTo("teste@test.com");
        }

        @Test
        @DisplayName("Deve retornar null se email não existe")
        void findByEmailIgnoreCase_NotFound() {
            Usuario result = usuarioRepository.findByEmailIgnoreCase("fantasma@test.com");
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("DEVE FALHAR: Deve lançar exceção se emails exatos duplicados forem encontrados")
        void findByEmailIgnoreCase_ShouldFailOnExactDuplicates() {
            // Reutilizando o helper, mas mudando o email manualmente
            Usuario user1 = createAndPersistUser("User A", "111", "Dev", true);
            user1.setEmail("email.duplicado@test.com");
            entityManager.persist(user1);

            Usuario user2 = createAndPersistUser("User B", "222", "QA", true);
            user2.setEmail("email.duplicado@test.com");
            entityManager.persist(user2);
            entityManager.flush();

            assertThrows(IncorrectResultSizeDataAccessException.class, () -> {
                usuarioRepository.findByEmailIgnoreCase("email.duplicado@test.com");
            });
        }
    }

    // --- Testes para findByCpf (Retorna Usuario ou null) ---
    @Nested
    @DisplayName("Testes para findByCpfIgnoreCase")
    class FindByCpfTests {
        @Test
        @DisplayName("Deve retornar usuário único pelo CPF")
        void findByCpf_Success() {
            Usuario user = createAndPersistUser("Teste", "12345678900", "Dev", true);

            Usuario result = usuarioRepository.findByCpfIgnoreCase("12345678900");

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(user.getId());
        }

        @Test
        @DisplayName("Deve retornar null se CPF não existe")
        void findByCpf_NotFound() {
            Usuario result = usuarioRepository.findByCpfIgnoreCase("99999999999");
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("DEVE FALHAR: Deve lançar exceção se CPFs exatos duplicados forem encontrados")
        void findByCpf_ShouldFailOnExactDuplicates() {
            createAndPersistUser("User A", "12345678900", "Dev", true);
            createAndPersistUser("User B", "12345678900", "QA", true);

            assertThrows(IncorrectResultSizeDataAccessException.class, () -> {
                usuarioRepository.findByCpfIgnoreCase("12345678900");
            });
        }
    }

    // --- Testes para findByCargoContainsIgnoreCase (Retorna List) ---
    @Nested
    @DisplayName("Testes para findByCargoContainsIgnoreCase")
    class FindByCargoTests {
        @Test
        @DisplayName("Deve retornar lista de usuários por cargo")
        void findByCargoContainsIgnoreCase_Success() {
            createAndPersistUser("Ana", "111", "Gerente de Vendas", true);
            createAndPersistUser("Beto", "222", "Gerente de Contas", true);
            createAndPersistUser("Carla", "333", "Vendedor", true);

            List<Usuario> result = usuarioRepository.findByCargoContainsIgnoreCase("gerente");

            assertThat(result).hasSize(2);
            assertThat(result).extracting(Usuario::getNome).contains("Ana", "Beto");
        }

        @Test
        @DisplayName("Deve retornar lista vazia se cargo não existe")
        void findByCargoContainsIgnoreCase_NotFound() {
            createAndPersistUser("Carla", "333", "Vendedor", true);

            List<Usuario> result = usuarioRepository.findByCargoContainsIgnoreCase("gerente");

            assertThat(result).isEmpty();
        }
    }

    // --- Testes para findByAtivo (Retorna List) ---
    @Nested
    @DisplayName("Testes para findByAtivo")
    class FindByAtivoTests {
        @Test
        @DisplayName("Deve retornar lista de usuários ativos")
        void findByAtivo_True() {
            createAndPersistUser("Ana", "111", "Dev", true);
            createAndPersistUser("Beto", "222", "Dev", true);
            createAndPersistUser("Carla", "333", "Dev", false);

            List<Usuario> result = usuarioRepository.findByAtivo(true);

            assertThat(result).hasSize(2);
            assertThat(result).extracting(Usuario::getNome).contains("Ana", "Beto");
        }

        @Test
        @DisplayName("Deve retornar lista de usuários inativos")
        void findByAtivo_False() {
            createAndPersistUser("Ana", "111", "Dev", true);
            createAndPersistUser("Carla", "333", "Dev", false);

            List<Usuario> result = usuarioRepository.findByAtivo(false);

            assertThat(result).hasSize(1);
            assertThat(result.getFirst().getNome()).isEqualTo("Carla");
        }
    }

    // --- Testes para existsByCpf (Retorna boolean) ---
    @Nested
    @DisplayName("Testes para existsByCpf")
    class ExistsByCpfTests {
        @Test
        @DisplayName("Deve retornar true se CPF existe")
        void existsByCpf_True() {
            createAndPersistUser("Ana", "12345678900", "Dev", true);

            boolean result = usuarioRepository.existsByCpf("12345678900");

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Deve retornar false se CPF não existe")
        void existsByCpf_False() {
            boolean result = usuarioRepository.existsByCpf("99999999999");

            assertThat(result).isFalse();
        }
    }

    // --- Testes para setFoto e getFoto (Queries Customizadas) ---
    @Nested
    @DisplayName("Testes para setFoto e getFoto")
    class FotoTests {
        @Test
        @DisplayName("Deve salvar e buscar a foto de um usuário")
        void setAndGetFoto_Success() {
            Usuario user = createAndPersistUser("Ana", "111", "Dev", true);
            byte[] fotoBytes = "teste_de_foto".getBytes();

            // Ação: Salvar foto
            usuarioRepository.setFoto(user.getId(), fotoBytes);

            // Limpa o cache do EntityManager para forçar o SELECT a ir ao BD
            entityManager.flush();
            entityManager.clear();

            // Ação: Buscar foto
            byte[] resultFoto = usuarioRepository.getFoto(user.getId());

            // Verificação
            assertThat(resultFoto).isNotNull();
            assertThat(resultFoto).isEqualTo(fotoBytes);
        }

        @Test
        @DisplayName("Deve retornar null se usuário não tem foto")
        void getFoto_NoFoto() {
            Usuario user = createAndPersistUser("Ana", "111", "Dev", true);
            // Nenhuma foto foi salva

            byte[] resultFoto = usuarioRepository.getFoto(user.getId());

            assertThat(resultFoto).isNull();
        }
    }
}