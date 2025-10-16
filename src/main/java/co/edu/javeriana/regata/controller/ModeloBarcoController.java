package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.service.ModeloBarcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modelos-barcos")
public class ModeloBarcoController {

    @Autowired
    private ModeloBarcoService modeloBarcoService;

    @PostMapping
    public ResponseEntity<ModeloBarco> crearModeloBarco(@RequestBody ModeloBarco modeloBarco) {
        ModeloBarco modeloCreado = modeloBarcoService.crearModeloBarco(
                modeloBarco.getId(), modeloBarco.getNombre(), modeloBarco.getColorHex());
        return ResponseEntity.ok(modeloCreado);
    }

    @GetMapping
    public ResponseEntity<List<ModeloBarco>> obtenerTodosModelosBarcos() {
        return ResponseEntity.ok(modeloBarcoService.obtenerTodosModelosBarcos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloBarco> obtenerModeloPorId(@PathVariable Long id) {
        Optional<ModeloBarco> mb = modeloBarcoService.obtenerModeloPorId(id);
        return mb.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloBarco> actualizarModeloBarco(@PathVariable Long id, @RequestBody ModeloBarco modeloBarco) {
        Optional<ModeloBarco> modeloExistente = modeloBarcoService.obtenerModeloPorId(id);
        if (modeloExistente.isPresent()) {
            modeloBarco.setId(id);
            ModeloBarco modeloActualizado = modeloBarcoService.actualizarModeloBarco(modeloBarco);
            return ResponseEntity.ok(modeloActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarModeloBarco(@PathVariable Long id) {
        boolean eliminado = modeloBarcoService.eliminarModeloBarco(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
