package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.service.GameService;
import co.edu.javeriana.regata.web.dto.MovimientoRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/game")
@CrossOrigin(origins = "http://localhost:4200")
public class GameRest {

    private final GameService gameService;

    public GameRest(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Lista los barcos que participan en el juego.
     */
    @GetMapping("/barcos")
    public List<Barco> listarBarcos() {
        return gameService.listarBarcosEnJuego();
    }

    /**
     * Aplica un movimiento a un barco.
     *
     * Recibe JSON:
     * {
     *   "barcoId": 51,
     *   "deltaVx": 1,
     *   "deltaVy": 0
     * }
     *
     * y devuelve el barco actualizado.
     */
    @PostMapping("/mover")
    public Barco mover(@RequestBody MovimientoRequest request) {
        return gameService.aplicarMovimiento(request);
    }
}
