package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.*;
import co.edu.javeriana.regata.repository.*;
import co.edu.javeriana.regata.web.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JuegoService {

    private final JugadorRepository jugadorRepo;
    private final ModeloBarcoRepository modeloRepo;
    private final BarcoRepository barcoRepo;
    private final MapaRepository mapaRepo;
    private final CeldaRepository celdaRepo;

    // Estado simple en memoria por jugador
    private final Map<Long, Long> barcoEnJuegoPorJugador = new HashMap<>();
    private final Map<Long, Long> mapaEnJuegoPorJugador  = new HashMap<>();
    private final Map<Long, Integer> turnoPorJugador     = new HashMap<>();

    public JuegoService(JugadorRepository jugadorRepo, ModeloBarcoRepository modeloRepo,
                        BarcoRepository barcoRepo, MapaRepository mapaRepo, CeldaRepository celdaRepo) {
        this.jugadorRepo = jugadorRepo;
        this.modeloRepo = modeloRepo;
        this.barcoRepo = barcoRepo;
        this.mapaRepo = mapaRepo;
        this.celdaRepo = celdaRepo;
    }

    @Transactional
    public EstadoJuegoDTO iniciar(Long jugadorId, Long modeloId, Long mapaId) {
        Jugador j = jugadorRepo.findById(jugadorId).orElseThrow();
        ModeloBarco m = modeloRepo.findById(modeloId).orElseThrow();
        Mapa mapa = mapaRepo.findById(mapaId).orElseThrow();

        // Buscar una celda PARTIDA
        List<Celda> celdas = celdaRepo.findByMapaId(mapa.getId());
        Optional<Celda> partida = celdas.stream()
                .filter(c -> c.getTipo() == TipoCelda.PARTIDA)
                .sorted(Comparator.comparingInt(Celda::getxCoord))
                .findFirst();

        int startX = partida.map(Celda::getxCoord).orElse(0);
        int startY = partida.map(Celda::getyCoord).orElse(0);

        Barco barco = new Barco();
        barco.setJugador(j);
        barco.setModelo(m);
        barco.setVelX(0);
        barco.setVelY(0);
        barco.setPosX(startX);
        barco.setPosY(startY);
        barco = barcoRepo.save(barco);

        barcoEnJuegoPorJugador.put(jugadorId, barco.getId());
        mapaEnJuegoPorJugador.put(jugadorId, mapa.getId());
        turnoPorJugador.put(jugadorId, 0);

        return armarEstado(jugadorId, "Partida iniciada.", "EN_CURSO");
    }

    @Transactional(readOnly = true)
    public EstadoJuegoDTO estado(Long jugadorId) {
        return armarEstado(jugadorId, "OK", "EN_CURSO");
    }

    @Transactional
    public EstadoJuegoDTO mover(Long jugadorId, int ax, int ay) {
        Long barcoId = barcoEnJuegoPorJugador.get(jugadorId);
        Long mapaId  = mapaEnJuegoPorJugador.get(jugadorId);
        if (barcoId == null || mapaId == null) throw new IllegalStateException("Primero inicia la partida.");

        Barco b = barcoRepo.findById(barcoId).orElseThrow();
        Mapa mapa = mapaRepo.findById(mapaId).orElseThrow();

        int newVx = clamp(b.getVelX() + ax, -999, 999); // sin límite duro salvo el del mapa
        int newVy = clamp(b.getVelY() + ay, -999, 999);

        int newX = b.getPosX() + newVx;
        int newY = b.getPosY() + newVy;

        boolean fuera = (newX < 0 || newX >= mapa.getAncho() || newY < 0 || newY >= mapa.getAlto());
        String estado = "EN_CURSO";
        String msg = "OK";

        if (!fuera) {
            TipoCelda destino = celdaRepo
                    .findByMapaIdAndXCoordAndYCoord(mapaId, newX, newY)
                    .map(Celda::getTipo).orElse(TipoCelda.PARED);

            if (destino == TipoCelda.PARED) {
                // choque: no avanza y resetea velocidad
                b.setVelX(0); b.setVelY(0);
                msg = "¡Choque contra pared!";
                estado = "COLISION";
            } else {
                b.setPosX(newX); b.setPosY(newY);
                b.setVelX(newVx); b.setVelY(newVy);
                if (destino == TipoCelda.META) {
                    estado = "GANADO";
                    msg = "¡Llegaste a la META! 🏁";
                }
            }
        } else {
            b.setVelX(0); b.setVelY(0);
            msg = "¡Te saliste del mapa!";
            estado = "COLISION";
        }

        barcoRepo.save(b);
        turnoPorJugador.put(jugadorId, turnoPorJugador.getOrDefault(jugadorId, 0) + 1);

        return armarEstado(jugadorId, msg, estado);
    }

    // ----------------- helpers -----------------

    private int clamp(int v, int lo, int hi){ return Math.max(lo, Math.min(hi, v)); }

    private EstadoJuegoDTO armarEstado(Long jugadorId, String mensaje, String estado) {
        Long barcoId = barcoEnJuegoPorJugador.get(jugadorId);
        Long mapaId  = mapaEnJuegoPorJugador.get(jugadorId);
        Barco barco = (barcoId != null) ? barcoRepo.findById(barcoId).orElse(null) : null;
        Mapa mapa   = (mapaId  != null) ? mapaRepo.findById(mapaId).orElse(null) : null;
        Jugador jug = jugadorRepo.findById(jugadorId).orElseThrow();

        EstadoJuegoDTO dto = new EstadoJuegoDTO();
        dto.setTurno(turnoPorJugador.getOrDefault(jugadorId, 0));
        dto.setEstado(estado);
        dto.setMensaje(mensaje);

        // Jugador
        dto.setJugador(new JugadorDTO(jug.getId(), jug.getNombre(), jug.getEmail()));

        // Mapa
        if (mapa != null) {
            MapaDTO md = new MapaDTO();
            md.setId(mapa.getId());
            md.setNombre(mapa.getNombre());
            md.setAncho(mapa.getAncho());
            md.setAlto(mapa.getAlto());
            md.setCeldas(
                celdaRepo.findByMapaId(mapa.getId()).stream()
                    .map(c -> new CeldaDTO(c.getxCoord(), c.getyCoord(), c.getTipo().name()))
                    .collect(Collectors.toList())
            );
            dto.setMapa(md);
        }

        // Barco
        if (barco != null) {
            BarcoDTO bd = new BarcoDTO();
            bd.setId(barco.getId());
            bd.setPosX(barco.getPosX());
            bd.setPosY(barco.getPosY());
            bd.setVelX(barco.getVelX());
            bd.setVelY(barco.getVelY());
            bd.setModeloId(barco.getModelo().getId());
            bd.setJugadorId(barco.getJugador().getId());
            bd.setNombreModelo(barco.getModelo().getNombre());
            bd.setColorHex(barco.getModelo().getColorHex());
            bd.setNombreJugador(jug.getNombre());
            dto.setBarco(bd);
        }

        return dto;
    }
}
