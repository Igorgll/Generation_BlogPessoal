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


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService; 

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> list = repository.findAll();
        if(list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado");
        }else {
            return ResponseEntity.ok(repository.findAll());
        }
    }
    
    
    @GetMapping("{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
        .orElse(ResponseEntity.notFound().build());
    } 

    @PostMapping("/logar")
    public ResponseEntity<UserLogin> Authentication(@RequestBody Optional<UserLogin> user){
        return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
        .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(usuarioService.validaBancoDuplicidade(usuario));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> Put(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuario));
    }
}
