package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import co.edu.javeriana.regata.service.AuthenticationService;
import co.edu.javeriana.regata.web.dto.JwtAuthenticationResponse;
import co.edu.javeriana.regata.web.dto.LoginDTO;
import co.edu.javeriana.regata.web.dto.UserRegistrationDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthRest {

    private final AuthenticationService authenticationService;
    private final JugadorRepository jugadorRepository;

    public AuthRest(AuthenticationService authenticationService,
                    JugadorRepository jugadorRepository) {
        this.authenticationService = authenticationService;
        this.jugadorRepository = jugadorRepository;
    }

    // ========= OPCIONAL: registro =========
    @PostMapping("/signup")
    public JwtAuthenticationResponse signup(@RequestBody UserRegistrationDTO request) {
        return authenticationService.signup(request);
    }

    // ========= LOGIN: devuelve token + datos del jugador =========
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDTO request) {

        // 1) Autenticar y generar el JWT
        JwtAuthenticationResponse auth = authenticationService.login(request);
        String token = auth.getToken();

        // 2) Buscar el jugador por email para enviar id/nombre/rol/email
        Jugador jugador = jugadorRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Jugador no encontrado: " + request.getEmail()));

        // 3) Construir respuesta completa
        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setId(jugador.getId());
        resp.setNombre(jugador.getNombre());
        resp.setEmail(jugador.getEmail());
        resp.setRol(jugador.getRol()); // "ADMIN" o "JUGADOR"

        return resp;
    }

    // ========= DTO de respuesta de login =========
    public static class LoginResponse {
        private String token;
        private Long id;
        private String nombre;
        private String email;
        private String rol;

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }
    }
}
