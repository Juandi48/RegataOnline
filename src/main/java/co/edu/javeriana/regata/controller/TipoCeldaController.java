package co.edu.javeriana.regata.controller;

import co.edu.javeriana.regata.domain.TipoCelda;
import co.edu.javeriana.regata.service.TipoCeldaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-celdas")
public class TipoCeldaController {

    @Autowired
    private TipoCeldaService tipoCeldaService;

    // Obtener todos los tipos de celda
    @GetMapping
    public ResponseEntity<List<TipoCelda>> obtenerTodosTiposDeCelda() {
        List<TipoCelda> tipos = tipoCeldaService.obtenerTodosTiposDeCelda();
        return ResponseEntity.ok(tipos);
    }

    // Obtener tipo de celda por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TipoCelda> obtenerTipoCeldaPorNombre(@PathVariable String nombre) {
        Optional<TipoCelda> tipoCelda = tipoCeldaService.obtenerTipoCeldaPorNombre(nombre);
        return tipoCelda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener tipo de celda por símbolo
    @GetMapping("/simbolo/{simbolo}")
    public ResponseEntity<TipoCelda> obtenerTipoCeldaPorSimbolo(@PathVariable String simbolo) {
        Optional<TipoCelda> tipoCelda = tipoCeldaService.obtenerTipoCeldaPorSimbolo(simbolo);
        return tipoCelda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
