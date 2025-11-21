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
    @NotBlank
    @Column(unique = true)
    private String email;

    /**
     * Password encriptado con BCrypt.
     * Lo marcamos con @JsonIgnore para que NUNCA salga en las respuestas JSON.
     */
    @NotBlank
    @JsonIgnore
    private String password;

    /**
     * Rol de seguridad: "ADMIN" o "JUGADOR".
     * En Spring Security se mapeará internamente a ROLE_ADMIN / ROLE_JUGADOR.
     */
    @NotBlank
    private String rol;

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL)
    @JsonIgnore // evita ciclo Jugador -> Barcos -> Jugador ...
    private List<Barco> barcos = new ArrayList<>();

    public Jugador() {
    }

    public Jugador(Long id, String nombre, String email, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public List<Barco> getBarcos() { return barcos; }
    public void setBarcos(List<Barco> barcos) { this.barcos = barcos; }
}
