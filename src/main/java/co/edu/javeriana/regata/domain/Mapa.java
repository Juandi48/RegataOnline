package co.edu.javeriana.regata.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Mapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int ancho;
    private int alto;

    @OneToMany(mappedBy = "mapa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Celda> celdas = new ArrayList<>();

    public Mapa() {}

    public Mapa(Long id, String nombre, int ancho, int alto) {
        this.id = id;
        this.nombre = nombre;
        this.ancho = ancho;
        this.alto = alto;
    }

    public void addCelda(Celda celda) {
        celda.setMapa(this);
        this.celdas.add(celda);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getAncho() { return ancho; }
    public void setAncho(int ancho) { this.ancho = ancho; }

    public int getAlto() { return alto; }
    public void setAlto(int alto) { this.alto = alto; }

    public List<Celda> getCeldas() { return celdas; }
    public void setCeldas(List<Celda> celdas) { this.celdas = celdas; }
}
