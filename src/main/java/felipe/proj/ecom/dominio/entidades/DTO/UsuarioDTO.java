package felipe.proj.ecom.dominio.entidades.DTO;

import felipe.proj.ecom.dominio.entidades.Role;

public class UsuarioDTO {
    private String username;
    private String senha;
    private Role role;

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
