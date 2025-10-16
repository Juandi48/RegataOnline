package co.edu.javeriana.regata.web.dto;

import java.util.List;

public class MapaDTO {
    private Long id;
    private String nombre;
    private int ancho;
    private int alto;
    private List<CeldaDTO> celdas;

    public MapaDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getAncho() { return ancho; }
    public void setAncho(int ancho) { this.ancho = ancho; }
    public int getAlto() { return alto; }
    public void setAlto(int alto) { this.alto = alto; }
    public List<CeldaDTO> getCeldas() { return celdas; }
    public void setCeldas(List<CeldaDTO> celdas) { this.celdas = celdas; }
}
