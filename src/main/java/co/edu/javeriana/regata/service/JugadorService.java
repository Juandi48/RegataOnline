package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final PasswordEncoder passwordEncoder;

    public JugadorService(JugadorRepository jugadorRepository,
                          PasswordEncoder passwordEncoder) {
        this.jugadorRepository = jugadorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --------- OPERACIONES PARA ADMIN ---------

    /**
     * Crea un nuevo jugador como ADMIN.
     */
    public Jugador crearJugadorAdmin(String nombre,
                                     String email,
                                     String password,
                                     String rol) {

        Jugador jugador = new Jugador();
        jugador.setNombre(nombre);
        jugador.setEmail(email);
        jugador.setPassword(passwordEncoder.encode(
                (password == null || password.isBlank()) ? "1234" : password
        ));
        jugador.setRol((rol == null || rol.isBlank()) ? "JUGADOR" : rol);

        return jugadorRepository.save(jugador);
    }

    public List<Jugador> obtenerTodosJugadores() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> obtenerJugadorPorId(Long id) {
        return jugadorRepository.findById(id);
    }

    public Jugador actualizarJugadorAdmin(Long id, Jugador datos) {
        return jugadorRepository.findById(id)
                .map(j -> {
                    j.setNombre(datos.getNombre());
                    j.setEmail(datos.getEmail());

                    if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
                        j.setPassword(passwordEncoder.encode(datos.getPassword()));
                    }
                    if (datos.getRol() != null && !datos.getRol().isBlank()) {
                        j.setRol(datos.getRol());
                    }

                    return jugadorRepository.save(j);
                })
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado con id " + id));
    }

    public void eliminarJugador(Long id) {
        if (jugadorRepository.existsById(id)) {
            jugadorRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Jugador no encontrado con id " + id);
        }
    }

    // --------- OPERACIONES PARA EL PROPIO JUGADOR ---------

    /**
     * Actualiza los datos de inicio de sesión del propio jugador (nombre, email, password).
     * emailActual se obtiene del principal autenticado (SecurityContext).
     */
    public Jugador actualizarPerfilJugador(String emailActual,
                                           String nombre,
                                           String nuevoEmail,
                                           String nuevaPassword) {

        Jugador jugador = jugadorRepository.findByEmail(emailActual)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado: " + emailActual));

        if (nombre != null && !nombre.isBlank()) {
            jugador.setNombre(nombre);
        }
        if (nuevoEmail != null && !nuevoEmail.isBlank()) {
            jugador.setEmail(nuevoEmail);
        }
        if (nuevaPassword != null && !nuevaPassword.isBlank()) {
            jugador.setPassword(passwordEncoder.encode(nuevaPassword));
        }

        // ¡Ojo! aquí NO se cambia el rol
        return jugadorRepository.save(jugador);
    }
}
