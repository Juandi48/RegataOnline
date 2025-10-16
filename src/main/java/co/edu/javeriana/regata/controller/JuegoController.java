package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.service.JuegoService;
import co.edu.javeriana.regata.web.dto.EstadoJuegoDTO;
import co.edu.javeriana.regata.web.dto.MovimientoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/juego")
public class JuegoController {

    private final JuegoService juegoService;

    public JuegoController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<EstadoJuegoDTO> iniciar(
            @RequestParam Long jugadorId,
            @RequestParam Long modeloId,
            @RequestParam Long mapaId
    ) {
        return ResponseEntity.ok(juegoService.iniciar(jugadorId, modeloId, mapaId));
    }

    @GetMapping("/estado")
    public ResponseEntity<EstadoJuegoDTO> estado(@RequestParam Long jugadorId) {
        return ResponseEntity.ok(juegoService.estado(jugadorId));
    }

    @PostMapping("/mover")
    public ResponseEntity<EstadoJuegoDTO> mover(
            @RequestParam Long jugadorId,
            @RequestBody MovimientoRequest req
    ) {
        return ResponseEntity.ok(juegoService.mover(jugadorId, req.ax, req.ay));
    }
}
