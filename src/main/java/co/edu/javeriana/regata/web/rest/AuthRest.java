package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthRest {

    private final JugadorRepository jugadorRepository;

    public AuthRest(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    /**
     * Devuelve la información del usuario autenticado (Jugador),
     * incluyendo su rol, para que el frontend sepa si es ADMIN o JUGADOR.
     */
    @GetMapping("/me")
    public Jugador me(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        return jugadorRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado: " + email));
    }
}
