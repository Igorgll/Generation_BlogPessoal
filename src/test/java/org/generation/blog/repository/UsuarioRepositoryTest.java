package org.generation.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blog.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();

        usuarioRepository.save(new Usuario(0L, "José da Silva", "josezin", "joao@email.com.br", "123456"));

        usuarioRepository.save(new Usuario(0L, "Eduardo Silva", "eduardin", "manuela@email.com.br", "123456"));

        usuarioRepository.save(new Usuario(0L, "Paulo Silva", "paulin", "adriana@email.com.br", "123456"));

        usuarioRepository.save(new Usuario(0L, "Roberto Rodrigues", "robertin", "paulo@email.com.br", "123456"));
    }

    @Test
    @DisplayName("Retorna 1 usuario")
    public void deveRetornarUmUsuario() {

        Optional<Usuario> usuario = usuarioRepository.findByUsuario("eduardin");
        assertTrue(usuario.get().getUsuario().equals("eduardin"));
    }

    @Test
    @DisplayName("Retorna 3 usuarios")
    public void deveRetornarTresUsuarios() {
        List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
        assertEquals(3, listaDeUsuarios.size()); // se retornar 3 o test será aprovado
        assertTrue(listaDeUsuarios.get(0).getNome().equals("José da Silva"));
        assertTrue(listaDeUsuarios.get(1).getNome().equals("Eduardo Silva"));
        assertTrue(listaDeUsuarios.get(2).getNome().equals("Paulo Silva"));
    }

}
