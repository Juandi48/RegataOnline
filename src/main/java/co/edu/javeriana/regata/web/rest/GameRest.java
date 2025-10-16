package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.service.GameService;
import co.edu.javeriana.regata.web.dto.EstadoJuegoDTO;
import co.edu.javeriana.regata.web.dto.MovimientoDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/game")
public class GameRest {

    private final GameService gameService;

    public GameRest(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public EstadoJuegoDTO start(@RequestParam Long mapaId, @RequestParam Long barcoId) {
        return gameService.iniciar(mapaId, barcoId);
    }

    @GetMapping("/state")
    public EstadoJuegoDTO state() {
        return gameService.estadoActual();
    }

    @PostMapping("/move")
    public EstadoJuegoDTO move(@RequestBody MovimientoDTO mov) {
        return gameService.mover(mov.getDeltaVX(), mov.getDeltaVY());
    }
}
