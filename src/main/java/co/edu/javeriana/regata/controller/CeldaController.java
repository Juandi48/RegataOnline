package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.Celda;
import co.edu.javeriana.regata.service.CeldaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/celdas")
public class CeldaController {

    @Autowired
    private CeldaService celdaService;

    @GetMapping("/todas")
    public ResponseEntity<List<Celda>> obtenerTodasLasCeldas() {
        List<Celda> celdas = celdaService.obtenerTodasLasCeldas();
        return ResponseEntity.ok(celdas);  
    }

    @PostMapping
    public ResponseEntity<Celda> crearCelda(@RequestBody Celda celda) {
        Celda nuevaCelda = celdaService.crearCelda(celda.getxCoord(), celda.getyCoord(), celda.getTipo(), celda.getMapa());
        return ResponseEntity.ok(nuevaCelda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Celda> actualizarCelda(@PathVariable Long id, @RequestBody Celda celda) {
        Celda celdaActualizada = celdaService.actualizarCelda(id, celda.getxCoord(), celda.getyCoord(), celda.getTipo(), celda.getMapa());
        return (celdaActualizada != null) ? ResponseEntity.ok(celdaActualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCelda(@PathVariable Long id) {
        boolean eliminado = celdaService.eliminarCelda(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
