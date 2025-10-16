package co.edu.javeriana.regata.web.dto;

public class ModeloBarcoDTO {
    private Long id;
    private String nombre;
    private String colorHex;

    public ModeloBarcoDTO() {}

    public ModeloBarcoDTO(Long id, String nombre, String colorHex) {
        this.id = id;
        this.nombre = nombre;
        this.colorHex = colorHex;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
}
