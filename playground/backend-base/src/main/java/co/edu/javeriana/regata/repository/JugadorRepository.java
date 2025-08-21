package co.edu.javeriana.regata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.javeriana.regata.domain.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {}
