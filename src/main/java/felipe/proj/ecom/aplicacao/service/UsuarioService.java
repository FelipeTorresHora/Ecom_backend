package felipe.proj.ecom.aplicacao.service;

import felipe.proj.ecom.dominio.entidades.Role;
import felipe.proj.ecom.dominio.entidades.Usuario;
import felipe.proj.ecom.infraestrutura.excecao.ResourceNotFoundException;
import felipe.proj.ecom.infraestrutura.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario registerUser(String username, String password, Role role) {
        if (usuarioRepository.findByusername(username).isPresent()) {
            throw new IllegalArgumentException("O nome de usuário já existe.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setSenha(password);
        usuario.setRoles(Collections.singleton(role));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByusername(String username) {
        return usuarioRepository.findByusername(username);
    }

    public void updatePassword(Long userId, String newPassword) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + userId));

        usuario.setSenha(newPassword);
        usuarioRepository.save(usuario);
    }
}
