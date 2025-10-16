package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.Celda;
import co.edu.javeriana.regata.domain.Mapa;
import co.edu.javeriana.regata.repository.MapaRepository;
import co.edu.javeriana.regata.web.dto.CeldaDTO;
import co.edu.javeriana.regata.web.dto.MapaDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mapas")
public class MapaRest {

    private final MapaRepository mapaRepository;

    public MapaRest(MapaRepository mapaRepository) {
        this.mapaRepository = mapaRepository;
    }

    @GetMapping
    public List<MapaDTO> listar() {
        List<Mapa> mapas = mapaRepository.findAll();
        List<MapaDTO> out = new ArrayList<>();
        for (Mapa m : mapas) {
            MapaDTO dto = new MapaDTO();
            dto.setId(m.getId());
            dto.setNombre(m.getNombre());
            dto.setAncho(m.getAncho());
            dto.setAlto(m.getAlto());
            out.add(dto);
        }
        return out;
    }

    @GetMapping("/{id}")
    public MapaDTO obtener(@PathVariable Long id) {
        Mapa m = mapaRepository.findById(id).orElseThrow();

        MapaDTO dto = new MapaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setAncho(m.getAncho());
        dto.setAlto(m.getAlto());

        List<CeldaDTO> celdas = new ArrayList<>();
        for (Celda c : m.getCeldas()) {
            celdas.add(new CeldaDTO(c.getxCoord(), c.getyCoord(), c.getTipo().name()));
        }
        dto.setCeldas(celdas);

        return dto;
    }
}
