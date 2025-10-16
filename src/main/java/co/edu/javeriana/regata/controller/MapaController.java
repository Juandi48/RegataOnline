package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.Mapa;
import co.edu.javeriana.regata.service.MapaService;
import co.edu.javeriana.regata.web.dto.MapaSaveDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/mapas")
public class MapaController {

    private final MapaService mapaService;

    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    // (Tus métodos CRUD existentes)

    /** Crea un mapa junto con todas sus celdas */
    @PostMapping("/completo")
    @Transactional
    public ResponseEntity<Mapa> crearMapaCompleto(@RequestBody MapaSaveDTO dto) {
        Mapa creado = mapaService.crearMapaConCeldas(dto);
        return ResponseEntity.created(URI.create("/api/mapas/" + creado.getId())).body(creado);
    }

    /** Reemplaza TODAS las celdas de un mapa existente (y opcionalmente nombre/ancho/alto) */
    @PutMapping("/{id}/completo")
    @Transactional
    public ResponseEntity<Mapa> reemplazarMapaCompleto(@PathVariable Long id, @RequestBody MapaSaveDTO dto) {
        return mapaService.reemplazarCeldas(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
