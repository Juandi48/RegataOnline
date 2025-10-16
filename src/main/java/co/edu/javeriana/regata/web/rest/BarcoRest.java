package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.repository.BarcoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/barcos")
public class BarcoRest {

    private final BarcoRepository barcoRepository;

    public BarcoRest(BarcoRepository barcoRepository) {
        this.barcoRepository = barcoRepository;
    }

    @GetMapping
    public List<Barco> listar() {
        return barcoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Barco get(@PathVariable Long id) {
        return barcoRepository.findById(id).orElseThrow();
    }
}
