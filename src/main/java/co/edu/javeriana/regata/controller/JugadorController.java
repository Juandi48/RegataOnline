package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@RequestBody Jugador jugador) {
        Jugador jugadorCreado = jugadorService.crearJugador(jugador.getId(), jugador.getNombre(), jugador.getEmail());
        return ResponseEntity.ok(jugadorCreado);
    }

    @GetMapping
    public ResponseEntity<List<Jugador>> obtenerTodosJugadores() {
        List<Jugador> jugadores = jugadorService.obtenerTodosJugadores();
        return ResponseEntity.ok(jugadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerJugadorPorId(@PathVariable Long id) {
        Optional<Jugador> jugador = jugadorService.obtenerJugadorPorId(id);
        return jugador.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizarJugador(@PathVariable Long id, @RequestBody Jugador jugador) {
        Optional<Jugador> jugadorExistente = jugadorService.obtenerJugadorPorId(id);
        if (jugadorExistente.isPresent()) {
            jugador.setId(id); 
            Jugador jugadorActualizado = jugadorService.actualizarJugador(jugador);
            return ResponseEntity.ok(jugadorActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable Long id) {
        boolean eliminado = jugadorService.eliminarJugador(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
