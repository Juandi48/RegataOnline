package co.edu.javeriana.regata.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class ModeloBarco {

    public ModeloBarco() {}

    public ModeloBarco(Long id, @NotBlank String nombre, @NotBlank String colorHex) {
        this.id = id;
        this.nombre = nombre;
        this.colorHex = colorHex;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String colorHex;

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getNombre(){ return nombre; }
    public void setNombre(String nombre){ this.nombre = nombre; }

    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
}
