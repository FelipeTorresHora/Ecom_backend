package felipe.proj.ecom.infraestrutura.repositorio;

import felipe.proj.ecom.dominio.entidades.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findAllBydatavendaBetween(LocalDateTime start, LocalDateTime end);
}