package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jugadores")
public class JugadorRest {

    private final JugadorRepository repo;

    public JugadorRest(JugadorRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Jugador> listar() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Jugador get(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }
}
