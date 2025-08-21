package co.edu.javeriana.regata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.domain.Jugador;

public interface BarcoRepository extends JpaRepository<Barco, Long> {
    List<Barco> findByJugador(Jugador jugador);
}
