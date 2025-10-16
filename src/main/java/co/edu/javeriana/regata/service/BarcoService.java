package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.BarcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarcoService {

    @Autowired
    private BarcoRepository barcoRepository;

    // Crear un barco nuevo
    public Barco crearBarco(ModeloBarco modelo, Jugador jugador, int velX, int velY, int posX, int posY) {
        Barco barco = new Barco(modelo, jugador, velX, velY, posX, posY);
        return barcoRepository.save(barco);
    }

    // Obtener un barco por su ID
    public Optional<Barco> obtenerBarcoPorId(Long id) {
        return barcoRepository.findById(id);
    }

    // Actualizar un barco existente
    public Barco actualizarBarco(Long id, ModeloBarco modelo, Jugador jugador, int velX, int velY, int posX, int posY) {
        return barcoRepository.findById(id)
                .map(barco -> {
                    barco.setModelo(modelo);
                    barco.setJugador(jugador);
                    barco.setVelX(velX);
                    barco.setVelY(velY);
                    barco.setPosX(posX);
                    barco.setPosY(posY);
                    return barcoRepository.save(barco);
                })
                .orElse(null);
    }

    // Eliminar un barco
    public boolean eliminarBarco(Long id) {
        if (barcoRepository.existsById(id)) {
            barcoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ===== NUEVAS FUNCIONALIDADES =====

    // Listar todos los barcos
    public List<Barco> listarTodos() {
        return barcoRepository.findAll();
    }

    // Listar barcos por jugador
    public List<Barco> listarPorJugador(Long jugadorId) {
        return barcoRepository.findByJugadorId(jugadorId);
    }

    // Listar barcos por modelo
    public List<Barco> listarPorModelo(Long modeloId) {
        return barcoRepository.findByModeloId(modeloId);
    }

    // Listar barcos por jugador y modelo
    public List<Barco> listarPorJugadorYModelo(Long jugadorId, Long modeloId) {
        return barcoRepository.findByJugadorIdAndModeloId(jugadorId, modeloId);
    }
}
