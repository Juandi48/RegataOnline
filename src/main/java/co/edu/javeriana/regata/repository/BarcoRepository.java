package co.edu.javeriana.regata.repository;

import co.edu.javeriana.regata.domain.Barco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarcoRepository extends JpaRepository<Barco, Long> {
    List<Barco> findByJugadorId(Long jugadorId);
    List<Barco> findByModeloId(Long modeloId);
    List<Barco> findByJugadorIdAndModeloId(Long jugadorId, Long modeloId);
}
