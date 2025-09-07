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

    // Crear un nuevo modelo de barco
    @PostMapping
    public ResponseEntity<ModeloBarco> crearModeloBarco(@RequestBody ModeloBarco modeloBarco) {
        ModeloBarco modeloCreado = modeloBarcoService.crearModeloBarco(modeloBarco.getId(), modeloBarco.getNombre(), modeloBarco.getColorHex());
        return ResponseEntity.ok(modeloCreado);
    }

    // Obtener todos los modelos de barcos
    @GetMapping
    public ResponseEntity<List<ModeloBarco>> obtenerTodosModelosBarcos() {
        List<ModeloBarco> modelos = modeloBarcoService.obtenerTodosModelosBarcos();
        return ResponseEntity.ok(modelos);
    }

    // Actualizar un modelo de barco existente
    @PutMapping("/{id}")
    public ResponseEntity<ModeloBarco> actualizarModeloBarco(@PathVariable Long id, @RequestBody ModeloBarco modeloBarco) {
        Optional<ModeloBarco> modeloExistente = modeloBarcoService.obtenerModeloPorId(id);
        if (modeloExistente.isPresent()) {
            modeloBarco.setId(id);  // Asegurarse de que el ID no se pierda en la actualización
            ModeloBarco modeloActualizado = modeloBarcoService.actualizarModeloBarco(modeloBarco);
            return ResponseEntity.ok(modeloActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un modelo de barco
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarModeloBarco(@PathVariable Long id) {
        boolean eliminado = modeloBarcoService.eliminarModeloBarco(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
