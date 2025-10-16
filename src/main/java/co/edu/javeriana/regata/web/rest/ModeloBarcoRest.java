package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modelos")
public class ModeloBarcoRest {

    private final ModeloBarcoRepository repo;

    public ModeloBarcoRest(ModeloBarcoRepository repo) { this.repo = repo; }

    @GetMapping
    public List<ModeloBarco> listar() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ModeloBarco get(@PathVariable Long id) { return repo.findById(id).orElseThrow(); }
}
