package org.generation.blog.Service;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import java.util.Optional;

import org.generation.blog.model.Usuario;
import org.generation.blog.model.dtos.UserLogin;
import org.generation.blog.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Optional<Usuario> CadastrarUsuario(Usuario usuario) {

        Optional<Usuario> novoUsuario = repository.findByUsuario(usuario.getUsuario());

        if (novoUsuario.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
        } else {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String passwordEncoder = encoder.encode(usuario.getSenha());
            usuario.setSenha(passwordEncoder);

            return Optional.ofNullable(repository.save(usuario));
        }
    }

    public Optional<UserLogin> Logar(Optional<UserLogin> user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

        if (usuario.isPresent()) {
            if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

                String auth = user.get().getUsuario() + ":" + user.get().getSenha();
                byte[] encoderAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encoderAuth);
                
                user.get().setToken(authHeader);
                user.get().setNome(usuario.get().getNome());
                user.get().setUsuario(usuario.get().getUsuario());
                user.get().setSenha(usuario.get().getSenha());
                user.get().setFoto(usuario.get().getFoto());
                user.get().setTipo(usuario.get().getTipo());

                return user;
            }
        }

        return null;

    }
}