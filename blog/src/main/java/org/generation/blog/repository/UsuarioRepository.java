package org.generation.blog.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blog.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    public Usuario findByUsuarioContainingIgnoreCase(String usuario);

    public Optional <Usuario> findByUsuario(String usuario);
    
    public List <Usuario> findAllByNomeContainingIgnoreCase(String nome);
}
