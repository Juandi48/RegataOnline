package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Celda;
import co.edu.javeriana.regata.domain.Mapa;
import co.edu.javeriana.regata.domain.TipoCelda;
import co.edu.javeriana.regata.repository.CeldaRepository;
import co.edu.javeriana.regata.repository.MapaRepository;
import co.edu.javeriana.regata.web.dto.CeldaDTO;
import co.edu.javeriana.regata.web.dto.MapaSaveDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MapaService {

    private final MapaRepository mapaRepository;
    private final CeldaRepository celdaRepository;

    public MapaService(MapaRepository mapaRepository, CeldaRepository celdaRepository) {
        this.mapaRepository = mapaRepository;
        this.celdaRepository = celdaRepository;
    }

    // (Tus métodos existentes: crearMapa, obtenerTodos, etc.)

    @Transactional
    public Mapa crearMapaConCeldas(MapaSaveDTO dto) {
        // 1) Crear mapa
        Mapa mapa = new Mapa();
        mapa.setNombre(dto.getNombre());
        mapa.setAncho(dto.getAncho());
        mapa.setAlto(dto.getAlto());
        mapa = mapaRepository.save(mapa);

        // 2) Persistir celdas
        if (dto.getCeldas() != null) {
            for (CeldaDTO c : dto.getCeldas()) {
                Celda celda = new Celda();
                celda.setMapa(mapa);
                celda.setxCoord(c.getX());
                celda.setyCoord(c.getY());
                celda.setTipo(TipoCelda.valueOf(c.getTipo())); // debe venir AGUA/PARED/PARTIDA/META
                celdaRepository.save(celda);
            }
        }
        return mapa;
    }

    @Transactional
    public Optional<Mapa> reemplazarCeldas(Long mapaId, MapaSaveDTO dto) {
        return mapaRepository.findById(mapaId).map(mapa -> {
            // Actualizar metadatos si vienen
            if (dto.getNombre() != null) mapa.setNombre(dto.getNombre());
            if (dto.getAncho() > 0) mapa.setAncho(dto.getAncho());
            if (dto.getAlto() > 0) mapa.setAlto(dto.getAlto());
            mapaRepository.save(mapa);

            // Borrar celdas actuales y volver a insertarlas
            celdaRepository.deleteAll(mapa.getCeldas());
            mapa.getCeldas().clear();

            if (dto.getCeldas() != null) {
                for (CeldaDTO c : dto.getCeldas()) {
                    Celda celda = new Celda();
                    celda.setMapa(mapa);
                    celda.setxCoord(c.getX());
                    celda.setyCoord(c.getY());
                    celda.setTipo(TipoCelda.valueOf(c.getTipo()));
                    celdaRepository.save(celda);
                    mapa.getCeldas().add(celda);
                }
            }
            return mapa;
        });
    }
}
