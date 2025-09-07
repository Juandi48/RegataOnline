package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.BarcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BarcoService {

    @Autowired
    private BarcoRepository barcoRepository;

    public Barco crearBarco(ModeloBarco modelo, Jugador jugador, int velX, int velY, int posX, int posY) {
        Barco barco = new Barco(modelo, jugador, velX, velY, posX, posY);
        return barcoRepository.save(barco);
    }

    public Optional<Barco> obtenerBarcoPorId(Long id) {
        return barcoRepository.findById(id);
    }

    public Barco actualizarBarco(Long id, ModeloBarco modelo, Jugador jugador, int velX, int velY, int posX, int posY) {
        Optional<Barco> barcoExistente = barcoRepository.findById(id);
        if (barcoExistente.isPresent()) {
            Barco barco = barcoExistente.get();
            barco.setModelo(modelo);
            barco.setJugador(jugador);
            barco.setVelX(velX);
            barco.setVelY(velY);
            barco.setPosX(posX);
            barco.setPosY(posY);
            return barcoRepository.save(barco);
        }
        return null; 
    }

    public boolean eliminarBarco(Long id) {
        if (barcoRepository.existsById(id)) {
            barcoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

