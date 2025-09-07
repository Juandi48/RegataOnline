package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloBarcoService {

    @Autowired
    private ModeloBarcoRepository modeloBarcoRepository;

    // Crear un nuevo modelo de barco
    public ModeloBarco crearModeloBarco(Long id, String nombre, String colorHex) {
        ModeloBarco modeloBarco = new ModeloBarco(id, nombre, colorHex);
        return modeloBarcoRepository.save(modeloBarco);
    }

    // Obtener todos los modelos de barcos
    public List<ModeloBarco> obtenerTodosModelosBarcos() {
        return modeloBarcoRepository.findAll();
    }

    // Obtener un modelo de barco por ID
    public Optional<ModeloBarco> obtenerModeloPorId(Long id) {
        return modeloBarcoRepository.findById(id);
    }

    // Actualizar un modelo de barco
    public ModeloBarco actualizarModeloBarco(ModeloBarco modeloBarco) {
        return modeloBarcoRepository.save(modeloBarco);
    }

    // Eliminar un modelo de barco
    public boolean eliminarModeloBarco(Long id) {
        if (modeloBarcoRepository.existsById(id)) {
            modeloBarcoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
