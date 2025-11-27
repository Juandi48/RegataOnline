package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.service.JugadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    // ------------------- VALIDACIÓN MANUAL -------------------

    private void validarAdmin(UserDetails userDetails) {
        boolean esAdmin = userDetails.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin) {
            throw new RuntimeException("Acceso denegado. Se requiere rol ADMIN.");
        }
    }

    // ------------------- ENDPOINTS SOLO ADMIN -------------------

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador,
                                                @AuthenticationPrincipal UserDetails userDetails) {

        validarAdmin(userDetails);

        Jugador jugadorCreado = jugadorService.crearJugadorAdmin(
                jugador.getNombre(),
                jugador.getEmail(),
                jugador.getPassword(),
                jugador.getRol()
        );

        return ResponseEntity.ok(jugadorCreado);
    }

    @GetMapping
    public ResponseEntity<List<Jugador>> obtenerTodosJugadores(
            @AuthenticationPrincipal UserDetails userDetails) {

        validarAdmin(userDetails);

        List<Jugador> jugadores = jugadorService.obtenerTodosJugadores();
        return ResponseEntity.ok(jugadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerJugadorPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        validarAdmin(userDetails);

        Optional<Jugador> jugador = jugadorService.obtenerJugadorPorId(id);
        return jugador.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizarJugador(
            @PathVariable Long id,
            @RequestBody Jugador jugador,
            @AuthenticationPrincipal UserDetails userDetails) {

        validarAdmin(userDetails);

        Jugador jugadorActualizado = jugadorService.actualizarJugadorAdmin(id, jugador);
        return ResponseEntity.ok(jugadorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        validarAdmin(userDetails);

        jugadorService.eliminarJugador(id);
        return ResponseEntity.noContent().build();
    }
}
