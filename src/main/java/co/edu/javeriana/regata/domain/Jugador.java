package co.edu.javeriana.regata.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Email
    private String email;

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL)
    @JsonIgnore // ← evita ciclo Jugador -> Barcos -> Jugador ...
    private List<Barco> barcos = new ArrayList<>();

    public Jugador() {}

    public Jugador(Long id, @NotBlank String nombre, @Email String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Jugador(Long id, @NotBlank String nombre, @Email String email, List<Barco> barcos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.barcos = barcos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Barco> getBarcos() { return barcos; }
    public void setBarcos(List<Barco> barcos) { this.barcos = barcos; }
}
