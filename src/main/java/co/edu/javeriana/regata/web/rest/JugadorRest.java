package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jugadores")
@CrossOrigin(origins = "http://localhost:4200")
public class JugadorRest {

    private final JugadorService jugadorService;

    public JugadorRest(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    // ---------- LECTURA (ADMIN) ----------
    @GetMapping
    public List<Jugador> listar(@AuthenticationPrincipal UserDetails user) {
        validarAdmin(user);
        return jugadorService.obtenerTodosJugadores();
    }

    @GetMapping("/{id}")
    public Jugador get(@PathVariable Long id,
                       @AuthenticationPrincipal UserDetails user) {
        validarAdmin(user);
        return jugadorService.obtenerJugadorPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado: " + id));
    }

    // ---------- CREAR (ADMIN) ----------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jugador crear(@RequestBody @Valid Jugador datos,
                         @AuthenticationPrincipal UserDetails user) {
        validarAdmin(user);

        return jugadorService.crearJugadorAdmin(
                datos.getNombre(),
                datos.getEmail(),
                datos.getPassword(),
                datos.getRol()
        );
    }

    // ---------- ACTUALIZAR (ADMIN) ----------
    @PutMapping("/{id}")
    public Jugador actualizar(@PathVariable Long id,
                              @RequestBody @Valid Jugador datos,
                              @AuthenticationPrincipal UserDetails user) {

        validarAdmin(user);

        return jugadorService.actualizarJugadorAdmin(id, datos);
    }

    // ---------- ELIMINAR (ADMIN) ----------
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id,
                         @AuthenticationPrincipal UserDetails user) {
        validarAdmin(user);
        jugadorService.eliminarJugador(id);
    }

    // ---------- ACTUALIZAR PERFIL PROPIO (ADMIN o JUGADOR) ----------
    @PutMapping("/me")
    public Jugador actualizarPerfil(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Jugador datos) {

        // email del token
        String emailActual = userDetails.getUsername();

        return jugadorService.actualizarPerfilJugador(
                emailActual,
                datos.getNombre(),
                datos.getEmail(),
                datos.getPassword()
        );
    }

    // ---------- VALIDACIÓN MANUAL (sin SpEL) ----------
    private void validarAdmin(UserDetails userDetails) {
        boolean esAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin) {
            throw new RuntimeException("Acceso denegado. Se requiere rol ADMIN.");
        }
    }
}
