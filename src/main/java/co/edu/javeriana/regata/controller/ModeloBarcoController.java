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

    // Crear
    @PostMapping
    public ResponseEntity<ModeloBarco> crearModeloBarco(@RequestBody ModeloBarco modeloBarco) {
        ModeloBarco modeloCreado = modeloBarcoService.crearModeloBarco(
                modeloBarco.getId(),
                modeloBarco.getNombre(),
                modeloBarco.getColorHex()
        );
        return ResponseEntity.ok(modeloCreado);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<ModeloBarco>> obtenerTodosModelosBarcos() {
        List<ModeloBarco> modelos = modeloBarcoService.obtenerTodosModelosBarcos();
        return ResponseEntity.ok(modelos);
    }

    // === Variantes para obtener por ID ===

    // 1) /api/modelos-barcos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ModeloBarco> obtenerModeloBarcoPorId(@PathVariable Long id) {
        Optional<ModeloBarco> modelo = modeloBarcoService.obtenerModeloPorId(id);
        return modelo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 2) /api/modelos-barcos?id=1
    @GetMapping(params = "id")
    public ResponseEntity<ModeloBarco> obtenerModeloBarcoPorQuery(@RequestParam("id") Long id) {
        Optional<ModeloBarco> modelo = modeloBarcoService.obtenerModeloPorId(id);
        return modelo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3) /api/modelos-barcos/ver/{id}  (alias por si el front lo llama así)
    @GetMapping("/ver/{id}")
    public ResponseEntity<ModeloBarco> verModeloBarco(@PathVariable Long id) {
        Optional<ModeloBarco> modelo = modeloBarcoService.obtenerModeloPorId(id);
        return modelo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar
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

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarModeloBarco(@PathVariable Long id) {
        boolean eliminado = modeloBarcoService.eliminarModeloBarco(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
