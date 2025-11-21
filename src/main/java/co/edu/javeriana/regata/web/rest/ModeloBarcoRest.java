package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modelos")
public class ModeloBarcoRest {

    private final ModeloBarcoRepository repo;

    public ModeloBarcoRest(ModeloBarcoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ModeloBarco> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ModeloBarco get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    // Solo ADMIN puede crear
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ModeloBarco crear(@RequestBody ModeloBarco m) {
        return repo.save(m);
    }

    // Solo ADMIN puede actualizar
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ModeloBarco actualizar(@PathVariable Long id, @RequestBody ModeloBarco m) {
        ModeloBarco existente = repo.findById(id).orElseThrow();
        existente.setNombre(m.getNombre());
        existente.setColorHex(m.getColorHex());
        return repo.save(existente);
    }

    // Solo ADMIN puede eliminar
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
