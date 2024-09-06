package felipe.proj.ecom.infraestrutura.repositorio;

import felipe.proj.ecom.dominio.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNome(String nome);
}
