package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.Mapa;
import co.edu.javeriana.regata.service.MapaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mapas")
public class MapaController {

    @Autowired
    private MapaService mapaService;

    @PostMapping
    public ResponseEntity<Mapa> crearMapa(@RequestBody Mapa mapa) {
        Mapa mapaCreado = mapaService.crearMapa(mapa.getNombre(), mapa.getAncho(), mapa.getAlto());
        return ResponseEntity.ok(mapaCreado);
    }

    @GetMapping
    public ResponseEntity<List<Mapa>> obtenerTodosMapas() {
        List<Mapa> mapas = mapaService.obtenerTodosMapas();
        return ResponseEntity.ok(mapas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mapa> obtenerMapaPorId(@PathVariable Long id) {
        Optional<Mapa> mapa = mapaService.obtenerMapaPorId(id);
        return mapa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mapa> actualizarMapa(@PathVariable Long id, @RequestBody Mapa mapa) {
        Mapa mapaActualizado = mapaService.actualizarMapa(id, mapa.getNombre(), mapa.getAncho(), mapa.getAlto());
        return (mapaActualizado != null) ? ResponseEntity.ok(mapaActualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMapa(@PathVariable Long id) {
        boolean eliminado = mapaService.eliminarMapa(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
