package co.edu.javeriana.regata.repository;

import co.edu.javeriana.regata.domain.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}
