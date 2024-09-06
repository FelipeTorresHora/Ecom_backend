package felipe.proj.ecom.infraestrutura.repositorio;

import felipe.proj.ecom.dominio.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByusername(String username);
}