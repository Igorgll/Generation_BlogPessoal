package org.generation.blog.Service;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import java.util.Optional;

import org.generation.blog.model.UserLogin;
import org.generation.blog.model.Usuario;
import org.generation.blog.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository repository;

    public Usuario validaBancoDuplicidade (Usuario usuario ) {
        Usuario c1 = new Usuario();
        c1 = repository.findByUsuarioContainingIgnoreCase(usuario.getUsuario());
        if(c1 == null){
            
        System.out.println("Não existe esse usuário");    
        return CadastrarUsuario(usuario);
        

        }else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            System.out.println("Já existe");
            return c1;
        }
        
    }


    public Usuario CadastrarUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncoder);

        return repository.save(usuario);
    }
    
    public Optional<UserLogin> Logar(Optional<UserLogin> user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

        if(usuario.isPresent()){
            if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

                String auth = user.get().getUsuario() + ":" + user.get().getSenha();
                byte[]  encoderAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encoderAuth);

                user.get().setToken(authHeader);
                user.get().setNome(usuario.get().getNome());

                return user;
            }
        }

        return null;

    }


}
