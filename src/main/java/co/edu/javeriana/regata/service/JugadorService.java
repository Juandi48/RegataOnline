package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public Jugador crearJugador(Long id, String nombre, String email) {
        Jugador jugador = new Jugador(id, nombre, email);
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
