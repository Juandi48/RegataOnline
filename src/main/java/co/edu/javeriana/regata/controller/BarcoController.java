package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.service.BarcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/barcos")
public class BarcoController {

    @Autowired
    private BarcoService barcoService;

    @PostMapping
    public ResponseEntity<Barco> crearBarco(@RequestBody Barco barco) {
        Barco barcoCreado = barcoService.crearBarco(barco.getModelo(), barco.getJugador(), barco.getVelX(), barco.getVelY(), barco.getPosX(), barco.getPosY());
        return ResponseEntity.ok(barcoCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barco> obtenerBarcoPorId(@PathVariable Long id) {
        Optional<Barco> barco = barcoService.obtenerBarcoPorId(id);
        return barco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barco> actualizarBarco(@PathVariable Long id, @RequestBody Barco barco) {
        Barco barcoActualizado = barcoService.actualizarBarco(id, barco.getModelo(), barco.getJugador(), barco.getVelX(), barco.getVelY(), barco.getPosX(), barco.getPosY());
        return (barcoActualizado != null) ? ResponseEntity.ok(barcoActualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBarco(@PathVariable Long id) {
        boolean eliminado = barcoService.eliminarBarco(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
