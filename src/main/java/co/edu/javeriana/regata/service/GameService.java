package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.repository.BarcoRepository;
import co.edu.javeriana.regata.web.dto.MovimientoRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    private final BarcoRepository barcoRepository;

    public GameService(BarcoRepository barcoRepository) {
        this.barcoRepository = barcoRepository;
    }

    /**
     * Lista todos los barcos que están en el juego.
     * (por ahora simplemente todos los barcos de la BD)
     */
    @Transactional(readOnly = true)
    public List<Barco> listarBarcosEnJuego() {
        return barcoRepository.findAll();
    }

    /**
     * Aplica un movimiento a un barco siguiendo la regla del enunciado:
     *
     * v' = v + Δv   donde Δv ∈ {-1,0,1} en cada componente
     * p' = p + v'
     *
     * No se validan colisiones entre barcos (varios pueden ocupar la misma celda).
     * Aquí luego podrás agregar validaciones de paredes, meta, etc.
     */
    @Transactional
    public Barco aplicarMovimiento(MovimientoRequest req) {

        if (req.getDeltaVx() < -1 || req.getDeltaVx() > 1 ||
            req.getDeltaVy() < -1 || req.getDeltaVy() > 1) {
            throw new IllegalArgumentException("deltaVx y deltaVy deben estar en {-1,0,1}");
        }

        Barco barco = barcoRepository.findById(req.getBarcoId())
                .orElseThrow(() -> new IllegalArgumentException("Barco no encontrado: " + req.getBarcoId()));

        // velocidad actual
        int vx = barco.getVelX();
        int vy = barco.getVelY();

        // nueva velocidad
        int nuevoVx = vx + req.getDeltaVx();
        int nuevoVy = vy + req.getDeltaVy();

        // nueva posición
        int nuevoX = barco.getPosX() + nuevoVx;
        int nuevoY = barco.getPosY() + nuevoVy;

        // TODO: aquí luego puedes validar paredes, límites del mapa, meta, etc.

        barco.setVelX(nuevoVx);
        barco.setVelY(nuevoVy);
        barco.setPosX(nuevoX);
        barco.setPosY(nuevoY);

        return barcoRepository.save(barco);
    }
}
