package co.edu.javeriana.regata.web.dto;

import java.util.List;

public class MapaSaveDTO {
    private String nombre;
    private int ancho;
    private int alto;
    private List<CeldaDTO> celdas;

    public MapaSaveDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getAncho() { return ancho; }
    public void setAncho(int ancho) { this.ancho = ancho; }

    public int getAlto() { return alto; }
    public void setAlto(int alto) { this.alto = alto; }

    public List<CeldaDTO> getCeldas() { return celdas; }
    public void setCeldas(List<CeldaDTO> celdas) { this.celdas = celdas; }
}
