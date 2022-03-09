package org.generation.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
     
    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();
    }

    @Test 
    @Order(1)
    @DisplayName("Cadastrar um Usuário")
    public void deveCriarUmUsuario() {

        //GIVEN
        HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "Paulo Antunes", "paulinho", "123456"));
    
        //WHEN
        ResponseEntity<Usuario> resposta = testRestTemplate
        .exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

        //THEN
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }
}

