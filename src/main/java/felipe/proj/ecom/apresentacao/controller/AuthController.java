package felipe.proj.ecom.apresentacao.controller;

import felipe.proj.ecom.dominio.entidades.Usuario;
import felipe.proj.ecom.infraestrutura.seguranca.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    private JwtFilter jwtUtil;



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) throws Exception {

        var usernamePassword = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        // Gera o token JWT
        var token = jwtUtil.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        } catch (BadCredentialsException e) {
//            throw new Exception("Usuário ou senha inválidos", e);
//        }
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthResponse(jwt));
//        return ResponseEntity.ok("Usuário criado com sucesso");
//    }
//
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