package felipe.proj.ecom.apresentacao.controller;

import felipe.proj.ecom.aplicacao.service.UsuarioService;
import felipe.proj.ecom.dominio.entidades.DTO.UsuarioDTO;
import felipe.proj.ecom.dominio.entidades.Usuario;
import felipe.proj.ecom.infraestrutura.repositorio.UsuarioRepository;
import felipe.proj.ecom.infraestrutura.seguranca.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Usuario usuario) throws Exception {

        var usernamePassword = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getSenha());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioDTO usuarioDTO){
        if (usuarioService.existeUsuario(usuarioDTO.getUsername())) {
            return ResponseEntity.badRequest().body(null);
        }

        Usuario novoUsuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
//        // Lógica para resetar a senha com base no nome de usuário
//        return ResponseEntity.ok("Senha resetada com sucesso");
//    }

    public static class LoginResponse {
        private String token;

        public LoginResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;

        }
    }
}