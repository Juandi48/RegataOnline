package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.service.GameService;
import co.edu.javeriana.regata.web.dto.MovimientoRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
     * (Por ahora, todos los barcos).
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
     *   "ax": 1,
     *   "ay": 0
     * }
     *
     * y devuelve el barco actualizado.
     *
     * - Si el usuario NO es admin, solo puede mover barcos de su propiedad.
     * - Si es admin, puede mover cualquier barco (útil para pruebas).
     */
    @PostMapping("/mover")
    public Barco mover(@AuthenticationPrincipal UserDetails userDetails,
                       @RequestBody MovimientoRequest request) {

        String email = userDetails.getUsername();
        boolean esAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return gameService.aplicarMovimiento(request, email, esAdmin);
    }
}
