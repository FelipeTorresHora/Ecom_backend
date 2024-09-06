package felipe.proj.ecom.integracao.controller;

import felipe.proj.ecom.dominio.entidades.Role;
import felipe.proj.ecom.dominio.entidades.Usuario;
import felipe.proj.ecom.infraestrutura.repositorio.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    void testGetUsuarioByUsername() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("usuarioNovo");
        usuario.setSenha("senhaSegura");
        usuario.getRoles().add(Role.USUARIO);
        usuarioRepository.save(usuario);

        mockMvc.perform(get("/usuarios/usuarioNovo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("usuarioNovo"));
    }

    @Test
    void testUpdatePassword() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("usuarioNovo");
        usuario.setSenha("senhaAntiga");
        usuario.getRoles().add(Role.USUARIO);
        usuario = usuarioRepository.save(usuario);

        mockMvc.perform(put("/usuarios/" + usuario.getId() + "/senha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"novaSenhaSegura\""))
                .andExpect(status().isNoContent());

        Usuario usuarioAtualizado = usuarioRepository.findById(usuario.getId()).get();
        assertEquals("novaSenhaSegura", usuarioAtualizado.getSenha());
    }
}
