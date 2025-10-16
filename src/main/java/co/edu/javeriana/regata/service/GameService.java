package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.domain.Celda;
import co.edu.javeriana.regata.domain.Mapa;
import co.edu.javeriana.regata.domain.TipoCelda;
import co.edu.javeriana.regata.repository.BarcoRepository;
import co.edu.javeriana.regata.repository.CeldaRepository;
import co.edu.javeriana.regata.repository.MapaRepository;
import co.edu.javeriana.regata.web.dto.BarcoDTO;
import co.edu.javeriana.regata.web.dto.CeldaDTO;
import co.edu.javeriana.regata.web.dto.EstadoJuegoDTO;
import co.edu.javeriana.regata.web.dto.MapaDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class GameService {

    private final MapaRepository mapaRepository;
    private final CeldaRepository celdaRepository;
    private final BarcoRepository barcoRepository;

    private Long mapaIdActual;
    private Long barcoIdActual;
    private int turno = 0;
    private String estado = "EN_CURSO";

    public GameService(MapaRepository mapaRepository,
            CeldaRepository celdaRepository,
            BarcoRepository barcoRepository) {
        this.mapaRepository = mapaRepository;
        this.celdaRepository = celdaRepository;
        this.barcoRepository = barcoRepository;
    }

    public EstadoJuegoDTO iniciar(Long mapaId, Long barcoId) {
        this.mapaIdActual = mapaId;
        this.barcoIdActual = barcoId;
        this.turno = 0;
        this.estado = "EN_CURSO";

        Barco b = barcoRepository.findById(barcoId).orElseThrow();
        b.setVelX(0);
        b.setVelY(0);

        Mapa m = mapaRepository.findById(mapaId).orElseThrow();

        // Colocar el barco en la primera celda PARTIDA
        Celda start = m.getCeldas().stream()
                .filter(c -> c.getTipo() == TipoCelda.PARTIDA)
                .min(Comparator.comparingInt(Celda::getyCoord).thenComparingInt(Celda::getxCoord))
                .orElseThrow();

        b.setPosX(start.getxCoord());
        b.setPosY(start.getyCoord());
        barcoRepository.save(b);

        return buildEstado(m, b);
    }

    public EstadoJuegoDTO estadoActual() {
        if (mapaIdActual == null || barcoIdActual == null) {
            throw new IllegalStateException("No hay partida iniciada. Llama /api/game/start");
        }
        Mapa m = mapaRepository.findById(mapaIdActual).orElseThrow();
        Barco b = barcoRepository.findById(barcoIdActual).orElseThrow();
        return buildEstado(m, b);
    }

    public EstadoJuegoDTO mover(int dVX, int dVY) {
        if (!"EN_CURSO".equals(estado)) {
            return estadoActual();
        }

        Barco b = barcoRepository.findById(barcoIdActual).orElseThrow();
        Mapa m = mapaRepository.findById(mapaIdActual).orElseThrow();

        int nVx = b.getVelX() + dVX;
        int nVy = b.getVelY() + dVY;
        int nX = b.getPosX() + nVx;
        int nY = b.getPosY() + nVy;

        // Fuera de límites -> destruido
        if (nX < 0 || nX >= m.getAncho() || nY < 0 || nY >= m.getAlto()) {
            estado = "DESTRUIDO";
        } else {
            TipoCelda tipo = celdaRepository
                    .findByMapaAndXY(m, nX, nY)
                    .map(Celda::getTipo)
                    .orElse(TipoCelda.PARED);

            if (tipo == TipoCelda.PARED) {
                estado = "DESTRUIDO";
            } else {
                b.setPosX(nX);
                b.setPosY(nY);
                b.setVelX(nVx);
                b.setVelY(nVy);
                barcoRepository.save(b);
                if (tipo == TipoCelda.META) {
                    estado = "GANADO";
                }
            }
        }
        turno++;
        return buildEstado(m, b);
    }

    // ===== Helpers de mapeo a DTOs =====

    private EstadoJuegoDTO buildEstado(Mapa m, Barco b) {
        EstadoJuegoDTO dto = new EstadoJuegoDTO();
        dto.setTurno(turno);
        dto.setEstado(estado);
        dto.setMapa(toMapaDTO(m));
        dto.setBarco(toBarcoDTO(b));
        return dto;
    }

    private MapaDTO toMapaDTO(Mapa m) {
        MapaDTO dto = new MapaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setAncho(m.getAncho());
        dto.setAlto(m.getAlto());

        List<CeldaDTO> lista = new ArrayList<>();
        for (Celda c : m.getCeldas()) {
            String tipo = c.getTipo().name();
            lista.add(new CeldaDTO(c.getxCoord(), c.getyCoord(), tipo));
        }
        dto.setCeldas(lista);
        return dto;
    }

    private BarcoDTO toBarcoDTO(Barco b) {
        BarcoDTO dto = new BarcoDTO();
        dto.setId(b.getId());
        if (b.getModelo() != null) {
            dto.setModeloId(b.getModelo().getId());
            dto.setNombreModelo(b.getModelo().getNombre());
            dto.setColorHex(b.getModelo().getColorHex());
        }
        if (b.getJugador() != null) {
            dto.setJugadorId(b.getJugador().getId());
            dto.setNombreJugador(b.getJugador().getNombre());
        }
        dto.setVelX(b.getVelX());
        dto.setVelY(b.getVelY());
        dto.setPosX(b.getPosX());
        dto.setPosY(b.getPosY());
        return dto;
    }
}
