package org.generation.blog.controller;

import java.util.List;

import org.generation.blog.model.postagem;
import org.generation.blog.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*") // aceita requisições de qualquer origem
public class PostagemController {
    
    @Autowired
    private PostagemRepository repository;

    @GetMapping
    public ResponseEntity<List<postagem>> GetAll(){
        return ResponseEntity.ok(repository.findAll());
    }

}
