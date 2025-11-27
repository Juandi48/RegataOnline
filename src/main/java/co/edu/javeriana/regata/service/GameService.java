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
     * Versión antigua, sin validación de usuario.
     * La dejo por compatibilidad (por ejemplo, con tests).
     */
    public Barco aplicarMovimiento(MovimientoRequest req) {
        return procesarMovimiento(req);
    }

    /**
     * Versión con seguridad: verifica que
     *  - si NO es admin, solo pueda mover su propio barco.
     */
    public Barco aplicarMovimiento(MovimientoRequest req,
                                   String emailUsuario,
                                   boolean esAdmin) {

        Barco barco = barcoRepository.findById(req.getBarcoId())
                .orElseThrow(() ->
                        new RuntimeException("Barco no encontrado: " + req.getBarcoId()));

        if (!esAdmin) {
            String emailDueno = barco.getJugador().getEmail();
            if (!emailDueno.equalsIgnoreCase(emailUsuario)) {
                throw new RuntimeException("No puedes mover un barco que no es tuyo.");
            }
        }

        return procesarMovimiento(req, barco);
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
     *
     * NOTA: Aquí todavía NO se valida contra paredes ni bordes de mapa.
     * Eso se puede agregar luego cuando vinculemos Barco con Mapa.
     */
    private Barco procesarMovimiento(MovimientoRequest req) {

        Barco barco = barcoRepository.findById(req.getBarcoId())
                .orElseThrow(() ->
                        new RuntimeException("Barco no encontrado: " + req.getBarcoId()));

        return procesarMovimiento(req, barco);
    }

    private Barco procesarMovimiento(MovimientoRequest req, Barco barco) {

        int ax = req.getAx();
        int ay = req.getAy();

        // validamos que ax, ay ∈ {-1, 0, +1}
        if (ax < -1 || ax > 1 || ay < -1 || ay > 1) {
            throw new IllegalArgumentException("Las aceleraciones ax y ay deben ser -1, 0 o +1.");
        }

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
