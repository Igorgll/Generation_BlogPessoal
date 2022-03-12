package org.generation.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blog.Service.UsuarioService;
import org.generation.blog.model.Usuario;
import org.generation.blog.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("Cadastrar um Usuário")
    public void deveCriarUmUsuario() {

        // GIVEN
        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
                new Usuario(0L, "Paulo Antunes", "paulinho", "paulo_antunes@email.com.br", "123456"));

        // WHEN
        ResponseEntity<Usuario> resposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

        // THEN
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Não deve permitir duplicação de Usuário")
    public void naoDeveDuplicarUsuario() {
        usuarioService.CadastrarUsuario(new Usuario(0L, "Maria Santos", "Maria", "maria_silva@email.com.br", "123456"));
        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
                new Usuario(0L, "Maria Santos", "Maria", "maria_silva@email.com.br", "123456"));

        ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar",
                HttpMethod.POST, requisicao, Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("Alterar um usuario")
    public void deveAtualizarUmUsuario() {
        Optional<Usuario> usuarioCreate = usuarioService.CadastrarUsuario(
                new Usuario(0L, "Juliana Andrews", "Julinha", "juliana_andrews@email.com.br", "123456"));
        Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(),
                "Juliana Andrews Ramos", "Julinha123", "juliana_andrews@email.com.br", "123456");

        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> resposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
        assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());

    }

    @Test
    @Order(4)
    @DisplayName("Listar todas as Postagens")
    public void deveMostrarTodasPostagens() {
        usuarioService.CadastrarUsuario(new Usuario(0L,
                "Sabrina Sanches", "sabrina", "sabrina_sanches@email.com.br", "sabrina123"));

        usuarioService.CadastrarUsuario(new Usuario(0L,
                "Ricardo Marques", "ricardo", "ricardo_marques@email.com.br", "ricardo123"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root", "root")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

}
