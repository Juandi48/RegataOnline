package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.BarcoRepository;
import co.edu.javeriana.regata.repository.JugadorRepository;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;
import co.edu.javeriana.regata.web.dto.BarcoCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barcos")
@CrossOrigin(origins = "http://localhost:4200")
public class BarcoRest {

    private final BarcoRepository barcoRepository;
    private final JugadorRepository jugadorRepository;
    private final ModeloBarcoRepository modeloBarcoRepository;

    public BarcoRest(BarcoRepository barcoRepository,
                     JugadorRepository jugadorRepository,
                     ModeloBarcoRepository modeloBarcoRepository) {
        this.barcoRepository = barcoRepository;
        this.jugadorRepository = jugadorRepository;
        this.modeloBarcoRepository = modeloBarcoRepository;
    }

    // ---------- LECTURA ----------

    @GetMapping
    public List<Barco> listar() {
        return barcoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Barco get(@PathVariable Long id) {
        return barcoRepository.findById(id).orElseThrow();
    }

    // ---------- CREAR ----------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Barco crear(@RequestBody BarcoCreateDTO dto) {

        Jugador jugador = jugadorRepository.findById(dto.getJugadorId())
                .orElseThrow();
        ModeloBarco modelo = modeloBarcoRepository.findById(dto.getModeloId())
                .orElseThrow();

        Barco b = new Barco();
        b.setJugador(jugador);
        b.setModelo(modelo);
        b.setPosX(dto.getPosX());
        b.setPosY(dto.getPosY());
        b.setVelX(dto.getVelX());
        b.setVelY(dto.getVelY());

        return barcoRepository.save(b);
    }

    // ---------- ELIMINAR ----------

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        if (barcoRepository.existsById(id)) {
            barcoRepository.deleteById(id);
        }
    }
}
