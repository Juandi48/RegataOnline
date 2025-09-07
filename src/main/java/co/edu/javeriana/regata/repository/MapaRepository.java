package co.edu.javeriana.regata.repository;

import co.edu.javeriana.regata.domain.Mapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapaRepository extends JpaRepository<Mapa, Long> {
}
