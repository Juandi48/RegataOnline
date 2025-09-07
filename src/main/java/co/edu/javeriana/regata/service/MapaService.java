package co.edu.javeriana.regata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.javeriana.regata.domain.Mapa;
import co.edu.javeriana.regata.repository.MapaRepository;

@Service
public class MapaService {

    @Autowired
    private MapaRepository mapaRepository;

    public Mapa crearMapa(String nombre, int ancho, int alto) {
        Mapa mapa = new Mapa();
        mapa.setNombre(nombre);
        mapa.setAncho(ancho);
        mapa.setAlto(alto);
        return mapaRepository.save(mapa);
    }

    public List<Mapa> obtenerTodosMapas() {
        return mapaRepository.findAll();
    }

    public Optional<Mapa> obtenerMapaPorId(Long id) {
        return mapaRepository.findById(id);
    }

    public Mapa actualizarMapa(Long id, String nombre, int ancho, int alto) {
        Optional<Mapa> mapaExistente = mapaRepository.findById(id);
        if (mapaExistente.isPresent()) {
            Mapa mapa = mapaExistente.get();
            mapa.setNombre(nombre);
            mapa.setAncho(ancho);
            mapa.setAlto(alto);
            return mapaRepository.save(mapa);
        }
        return null; 
    }

    public boolean eliminarMapa(Long id) {
        if (mapaRepository.existsById(id)) {
            mapaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


