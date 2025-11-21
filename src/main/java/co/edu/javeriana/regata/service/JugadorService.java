package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo jugador.
     * Por simplicidad, le dejamos rol JUGADOR y una contraseña por defecto "1234"
     * (encriptada con BCrypt).
     */
    public Jugador crearJugador(Long id, String nombre, String email) {
        Jugador jugador = new Jugador();
        // normalmente dejarías que el ID se genere solo, pero conservo el parámetro
        // por si tu controlador lo sigue enviando
        jugador.setId(id);
        jugador.setNombre(nombre);
        jugador.setEmail(email);
        jugador.setRol("JUGADOR");
        jugador.setPassword(passwordEncoder.encode("1234"));
        return jugadorRepository.save(jugador);
    }

    public List<Jugador> obtenerTodosJugadores() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> obtenerJugadorPorId(Long id) {
        return jugadorRepository.findById(id);
    }

    public Jugador actualizarJugador(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public boolean eliminarJugador(Long id) {
        if (jugadorRepository.existsById(id)) {
            jugadorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
