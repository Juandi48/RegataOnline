package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jugadores")
@CrossOrigin(origins = "http://localhost:4200")
public class JugadorRest {

    private final JugadorRepository repo;
    private final PasswordEncoder passwordEncoder;

    public JugadorRest(JugadorRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------- LECTURA ----------

    @GetMapping
    public List<Jugador> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Jugador get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    // ---------- CREAR ----------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Jugador crear(@RequestBody @Valid Jugador j) {

        // Si no nos mandan contraseña/rol desde el front,
        // ponemos valores por defecto válidos.
        if (j.getPassword() == null || j.getPassword().isBlank()) {
            j.setPassword(passwordEncoder.encode("1234"));
        }
        if (j.getRol() == null || j.getRol().isBlank()) {
            j.setRol("JUGADOR");
        }

        return repo.save(j);
    }

    // ---------- ACTUALIZAR ----------

    @PutMapping("/{id}")
    public Jugador actualizar(@PathVariable Long id, @RequestBody @Valid Jugador datos) {
        return repo.findById(id).map(j -> {
            j.setNombre(datos.getNombre());
            j.setEmail(datos.getEmail());

            // Solo cambiar password/rol si vienen informados
            if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
                j.setPassword(passwordEncoder.encode(datos.getPassword()));
            }
            if (datos.getRol() != null && !datos.getRol().isBlank()) {
                j.setRol(datos.getRol());
            }

            return repo.save(j);
        }).orElseThrow();
    }

    // ---------- ELIMINAR ----------

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }
}
