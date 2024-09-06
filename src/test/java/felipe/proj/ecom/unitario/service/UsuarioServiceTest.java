package felipe.proj.ecom.unitario.service;

import felipe.proj.ecom.dominio.entidades.Usuario;
import felipe.proj.ecom.dominio.entidades.Role;
import felipe.proj.ecom.infraestrutura.repositorio.UsuarioRepository;
import felipe.proj.ecom.aplicacao.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        Usuario usuario = new Usuario();
        usuario.setUsername("usuarioNovo");
        usuario.setSenha("senhaSegura");
        usuario.getRoles().add(Role.USUARIO);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.registerUser("usuarioNovo", "senhaSegura", Role.USUARIO);

        assertEquals("usuarioNovo", result.getUsername());
        assertEquals("senhaSegura", result.getSenha());
        assertTrue(result.getRoles().contains(Role.USUARIO));
    }

    @Test
    void testFindByUsername_Success() {
        Usuario usuario = new Usuario();
        usuario.setUsername("usuarioNovo");

        when(usuarioRepository.findByusername("usuarioNovo")).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.findByusername("usuarioNovo");

        assertTrue(result.isPresent());
        assertEquals("usuarioNovo", result.get().getUsername());
    }

    @Test
    void testFindByUsername_NotFound() {
        when(usuarioRepository.findByusername("usuarioNaoExistente")).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.findByusername("usuarioNaoExistente");

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdatePassword_Success() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("usuarioNovo");
        usuario.setSenha("senhaAntiga");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.updatePassword(1L, "novaSenha");

        assertEquals("novaSenha", usuario.getSenha());
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
