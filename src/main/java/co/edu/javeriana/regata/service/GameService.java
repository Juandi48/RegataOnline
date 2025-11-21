package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.repository.BarcoRepository;
import co.edu.javeriana.regata.web.dto.MovimientoRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final BarcoRepository barcoRepository;

    public GameService(BarcoRepository barcoRepository) {
        this.barcoRepository = barcoRepository;
    }

    /**
     * Devuelve todos los barcos que están "en juego".
     * De momento tomamos todos los barcos de la BD;
     * si luego tienes un flag de carrera actual, aquí se filtra.
     */
    public List<Barco> listarBarcosEnJuego() {
        return barcoRepository.findAll();
    }

    /**
     * Método que usa GameRest: aplica un movimiento a un barco.
     */
    public Barco aplicarMovimiento(MovimientoRequest req) {
        return procesarMovimiento(req);
    }

    /**
     * Lógica de movimiento según el enunciado:
     *
     *  v = (vx, vy)
     *  a = (ax, ay)
     *
     *  v' = v + a
     *  p' = p + v'
     *
     * El barco parte de v = (0,0) y posición inicial que tengas en la BD.
     */
    public Barco procesarMovimiento(MovimientoRequest req) {

        Barco barco = barcoRepository.findById(req.getBarcoId())
                .orElseThrow(() -> new RuntimeException("Barco no encontrado: " + req.getBarcoId()));

        // aceleraciones elegidas por el jugador: -1, 0 o +1
        int ax = req.getAx();
        int ay = req.getAy();

        // nueva velocidad
        int nuevaVelX = barco.getVelX() + ax;
        int nuevaVelY = barco.getVelY() + ay;

        barco.setVelX(nuevaVelX);
        barco.setVelY(nuevaVelY);

        // nueva posición
        int nuevaPosX = barco.getPosX() + nuevaVelX;
        int nuevaPosY = barco.getPosY() + nuevaVelY;

        barco.setPosX(nuevaPosX);
        barco.setPosY(nuevaPosY);

        return barcoRepository.save(barco);
    }
}
