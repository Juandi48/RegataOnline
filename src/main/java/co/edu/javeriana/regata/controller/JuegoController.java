package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.repository.BarcoRepository;
import co.edu.javeriana.regata.web.dto.MovimientoRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/juego")
public class JuegoController {

    private final BarcoRepository barcoRepository;

    public JuegoController(BarcoRepository barcoRepository) {
        this.barcoRepository = barcoRepository;
    }

    /**
     * Aplica el movimiento de un turno a un barco:
     *  - v' = v + (ax, ay)
     *  - p' = p + v'
     * (aquí todavía no estamos validando paredes, meta, etc.)
     */
    @PostMapping("/mover")
    public Barco mover(@RequestBody MovimientoRequest req) {
        Barco barco = barcoRepository.findById(req.getBarcoId())
                .orElseThrow(() -> new RuntimeException("Barco no encontrado: " + req.getBarcoId()));

        // cambio de velocidad
        int newVx = barco.getVelX() + req.getAx();
        int newVy = barco.getVelY() + req.getAy();

        // actualizar velocidad
        barco.setVelX(newVx);
        barco.setVelY(newVy);

        // nueva posición: p' = p + v'
        int newX = barco.getPosX() + newVx;
        int newY = barco.getPosY() + newVy;

        barco.setPosX(newX);
        barco.setPosY(newY);

        return barcoRepository.save(barco);
    }
}
