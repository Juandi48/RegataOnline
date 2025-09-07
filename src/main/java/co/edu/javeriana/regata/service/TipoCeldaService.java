package co.edu.javeriana.regata.service;

import co.edu.javeriana.regata.domain.TipoCelda;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TipoCeldaService {

    public List<TipoCelda> obtenerTodosTiposDeCelda() {
        return Arrays.asList(TipoCelda.values());
    }

    public Optional<TipoCelda> obtenerTipoCeldaPorNombre(String nombre) {
        return Arrays.stream(TipoCelda.values())
                     .filter(tipo -> tipo.getNombre().equalsIgnoreCase(nombre))
                     .findFirst();
    }

    public Optional<TipoCelda> obtenerTipoCeldaPorSimbolo(String simbolo) {
        return Arrays.stream(TipoCelda.values())
                     .filter(tipo -> tipo.getSimbolo().equalsIgnoreCase(simbolo))
                     .findFirst();
    }
}

