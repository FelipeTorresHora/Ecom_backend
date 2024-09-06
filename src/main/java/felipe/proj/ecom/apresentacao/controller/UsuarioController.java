package felipe.proj.ecom.apresentacao.controller;

import felipe.proj.ecom.dominio.entidades.Usuario;
import felipe.proj.ecom.aplicacao.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> geatAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> getUsuarioByUsername(@PathVariable String username) {
        Optional<Usuario> usuario = usuarioService.findByusername(username);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{userId}/senha")
//    public ResponseEntity<Void> updatePassword(@PathVariable Long userId, @RequestBody String newPassword) {
//        usuarioService.updatePassword(userId, newPassword);
//        return ResponseEntity.noContent().build();
//    }
}

