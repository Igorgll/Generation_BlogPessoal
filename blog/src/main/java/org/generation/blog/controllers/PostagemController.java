package org.generation.blog.controllers;

import java.util.List;

import org.generation.blog.model.Postagem;
import org.generation.blog.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*") // aceita requisições de qualquer origem
public class PostagemController {
    
    @Autowired
    private PostagemRepository repository;

    @GetMapping
    public ResponseEntity<List<Postagem>> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping ("/titulo/{titulo}")
    public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
        return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
    }

    @GetMapping ("/{id}") // tudo que você precisa receber de volta fica entre {}
    public ResponseEntity<Postagem> getById(@PathVariable long id){
        return repository.findById(id)
            .map(resp -> ResponseEntity.ok(resp)) // -> array function
            .orElse(ResponseEntity.notFound().build()); // retorna erro notFound
    }

    @PostMapping
    public ResponseEntity<Postagem> post (@RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem)); 
    }

    @PutMapping 
    public ResponseEntity<Postagem> put (@RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
    }

    @DeleteMapping ("/{id}")
    public void delete(@PathVariable long id){
        repository.deleteById(id);
    }

}
