package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.BarcoRepository;
import co.edu.javeriana.regata.repository.JugadorRepository;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;
import co.edu.javeriana.regata.web.dto.BarcoCreateDTO;
import co.edu.javeriana.regata.web.dto.BarcoDTO;
import co.edu.javeriana.regata.web.mapper.BarcoMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/barcos")
@CrossOrigin(origins = "http://localhost:4200")
public class BarcoRestController {

    private final BarcoRepository barcoRepository;
    private final JugadorRepository jugadorRepository;
    private final ModeloBarcoRepository modeloRepository;
    private final BarcoMapper barcoMapper;

    public BarcoRestController(
            BarcoRepository barcoRepository,
            JugadorRepository jugadorRepository,
            ModeloBarcoRepository modeloRepository,
            BarcoMapper barcoMapper
    ) {
        this.barcoRepository = barcoRepository;
        this.jugadorRepository = jugadorRepository;
        this.modeloRepository = modeloRepository;
        this.barcoMapper = barcoMapper;
    }


    // ========= GET =========
    @GetMapping
    public List<BarcoDTO> listar() {
        return barcoRepository.findAll()
                .stream()
                .map(barcoMapper::toDto)     // <-- AQUI EL NOMBRE CORRECTO
                .collect(Collectors.toList());
    }


    // ========= POST =========
    @PostMapping
    public ResponseEntity<BarcoDTO> crear(@RequestBody BarcoCreateDTO dto) {

        System.out.println(">>> creando barco: jugadorId=" + dto.getJugadorId()
                + ", modeloId=" + dto.getModeloId());

        if (dto.getJugadorId() == null || dto.getModeloId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Jugador jugador = jugadorRepository.findById(dto.getJugadorId())
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));

        ModeloBarco modelo = modeloRepository.findById(dto.getModeloId())
                .orElseThrow(() -> new IllegalArgumentException("Modelo no encontrado"));

        Barco b = new Barco();
        b.setJugador(jugador);
        b.setModelo(modelo);
        b.setPosX(dto.getPosX());
        b.setPosY(dto.getPosY());
        b.setVelX(dto.getVelX());
        b.setVelY(dto.getVelY());

        Barco guardado = barcoRepository.save(b);

        BarcoDTO respuesta = barcoMapper.toDto(guardado);   // <-- AQUI TAMBIÉN CORRECTO

        return ResponseEntity
                .created(URI.create("/api/v1/barcos/" + guardado.getId()))
                .body(respuesta);
    }


    // ========= DELETE =========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!barcoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        barcoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
