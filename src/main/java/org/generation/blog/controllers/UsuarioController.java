package org.generation.blog.controllers;

import java.util.List;
import java.util.Optional;

import org.generation.blog.Service.UsuarioService;
import org.generation.blog.model.UserLogin;
import org.generation.blog.model.Usuario;
import org.generation.blog.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Recursos do Usuario", description = "Administração de uso do usuário no sistema")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository repository;

    @Operation(summary = "Busca lista de usuarios no sistema")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna todos Usuarios"),
			@ApiResponse(responseCode = "400", description = "Retorno sem Usuarios"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado");
        } else {
            return ResponseEntity.ok(repository.findAll());
        }
    }

    @Operation(summary = "Busca usuario por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna Usuario existente"),
			@ApiResponse(responseCode = "400", description = "Usuario inexistente"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Faz login do Usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario logado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Email ou senha inválidos"),
            @ApiResponse(responseCode = "422", description = "Usuario já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})

    @PostMapping("/logar")
    public ResponseEntity<UserLogin> Authentication(@RequestBody Optional<UserLogin> user) {
        return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Operation(summary = "Faz cadastro do Usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "422", description = "Usuario já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario) {
        return usuarioService.CadastrarUsuario(usuario).map(resp -> ResponseEntity.status(201).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Operation(summary = "Atualiza Usuario existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Retorna Usuario Atualizado"),
			@ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> Put(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuario));
    }
}
