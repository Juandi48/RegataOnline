package co.edu.javeriana.regata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.javeriana.regata.domain.Celda;
import org.springframework.stereotype.Repository;

@Repository
public interface CeldaRepository extends JpaRepository<Celda, Long> {
}
