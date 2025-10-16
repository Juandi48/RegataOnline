package co.edu.javeriana.regata.repository;

import co.edu.javeriana.regata.domain.Celda;
import co.edu.javeriana.regata.domain.Mapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CeldaRepository extends JpaRepository<Celda, Long> {

    // Tu query original (por Mapa y coordenadas)
    @Query("select c from Celda c where c.mapa = :mapa and c.xCoord = :x and c.yCoord = :y")
    Optional<Celda> findByMapaAndXY(@Param("mapa") Mapa mapa,
                                    @Param("x") int x,
                                    @Param("y") int y);

    // Conveniencias que usa el servicio de juego
    List<Celda> findByMapaId(Long mapaId);

    Optional<Celda> findByMapaIdAndXCoordAndYCoord(Long mapaId, int xCoord, int yCoord);
}
