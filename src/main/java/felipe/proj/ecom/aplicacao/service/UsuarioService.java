package felipe.proj.ecom.aplicacao.service;

import felipe.proj.ecom.dominio.entidades.DTO.UsuarioDTO;
import felipe.proj.ecom.dominio.entidades.Usuario;
import felipe.proj.ecom.infraestrutura.excecao.ResourceNotFoundException;
import felipe.proj.ecom.infraestrutura.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    public boolean existeUsuario(String username) {
        return usuarioRepository.findByUsername(username) != null;
    }

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDTO.getSenha());

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(usuarioDTO.getUsername());
        novoUsuario.setSenha(encryptedPassword);
        novoUsuario.setRole(usuarioDTO.getRole());

        return usuarioRepository.save(novoUsuario);
    }


    public UserDetails findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public void updatePassword(Long userId, String newPassword) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + userId));

        usuario.setSenha(newPassword);
        usuarioRepository.save(usuario);
    }
}
