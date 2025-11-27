package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import co.edu.javeriana.regata.web.dto.JwtAuthenticationResponse;
import co.edu.javeriana.regata.web.dto.LoginDTO;
import co.edu.javeriana.regata.web.dto.UserRegistrationDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JugadorRepository jugadorRepository;
    private final JwtService jwtService;     // 👈 asegúrate de tener este servicio

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JugadorRepository jugadorRepository,
                                 JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jugadorRepository = jugadorRepository;
        this.jwtService = jwtService;
    }

    public JwtAuthenticationResponse signup(UserRegistrationDTO request) {
        // Si ya tenías lógica de registro, déjala aquí.
        // Para no romper nada, puedes simplemente delegar al login
        // o implementar el registro real según tu proyecto.
        throw new UnsupportedOperationException("signup aún no implementado");
    }

    public JwtAuthenticationResponse login(LoginDTO request) {

        // 1. Autenticar usuario (email + password)
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails principal = (UserDetails) auth.getPrincipal();

        // 2. Buscar Jugador en la BD
        Jugador jugador = jugadorRepository.findByEmail(principal.getUsername())
                .orElseThrow(() ->
                        new IllegalArgumentException("Jugador no encontrado: " + principal.getUsername()));

        // 3. Generar token JWT
        String token = jwtService.generateToken(principal);

        // 4. Armar respuesta
        JwtAuthenticationResponse resp = new JwtAuthenticationResponse();
        resp.setToken(token);
        resp.setId(jugador.getId());
        resp.setNombre(jugador.getNombre());
        resp.setEmail(jugador.getEmail());
        resp.setRol(jugador.getRol());

        return resp;
    }
}
