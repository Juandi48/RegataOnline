package co.edu.javeriana.regata.domain;

import jakarta.persistence.*;

@Entity
public class Barco {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private ModeloBarco modelo;

    @ManyToOne(optional = false)
    private Jugador jugador;

    private int velX;
    private int velY;
    private int posX;
    private int posY;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ModeloBarco getModelo() { return modelo; }
    public void setModelo(ModeloBarco modelo) { this.modelo = modelo; }

    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }

    public int getVelX() { return velX; }
    public void setVelX(int velX) { this.velX = velX; }

    public int getVelY() { return velY; }
    public void setVelY(int velY) { this.velY = velY; }

    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }

    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }
}
