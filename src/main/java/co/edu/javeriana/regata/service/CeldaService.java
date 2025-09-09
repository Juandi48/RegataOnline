package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.Celda;
import co.edu.javeriana.regata.domain.Mapa;
import co.edu.javeriana.regata.domain.TipoCelda;
import co.edu.javeriana.regata.repository.CeldaRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CeldaService {

    @Autowired
    private CeldaRepository celdaRepository;

    public List<Celda> obtenerTodasLasCeldas() {
        return celdaRepository.findAll();  
    }

    public Celda crearCelda(int xCoord, int yCoord, TipoCelda tipo, Mapa mapa) {
        Celda celda = new Celda(xCoord, yCoord, tipo, mapa);
        return celdaRepository.save(celda);
    }

    public Celda actualizarCelda(Long id, int xCoord, int yCoord, TipoCelda tipo, Mapa mapa) {
        Celda celdaExistente = celdaRepository.findById(id).orElse(null);
        if (celdaExistente != null) {
            celdaExistente.setxCoord(xCoord);
            celdaExistente.setyCoord(yCoord);
            celdaExistente.setTipo(tipo);
            celdaExistente.setMapa(mapa);
            return celdaRepository.save(celdaExistente);
        }
        return null; 
    }

    public boolean eliminarCelda(Long id) {
        if (celdaRepository.existsById(id)) {
            celdaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

