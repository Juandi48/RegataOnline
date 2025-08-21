package co.edu.javeriana.regata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import co.edu.javeriana.regata.domain.Celda;
import co.edu.javeriana.regata.domain.Mapa;

public interface CeldaRepository extends JpaRepository<Celda, Long> {
    List<Celda> findByMapa(Mapa mapa);
}
