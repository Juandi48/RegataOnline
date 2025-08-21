package co.edu.javeriana.regata.domain;

import jakarta.persistence.*;

@Entity
@Table(indexes = {@Index(columnList = "xCoord,yCoord")})
public class Celda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int xCoord;
    private int yCoord;

    @Enumerated(EnumType.STRING)
    private TipoCelda tipo;

    @ManyToOne(optional = false)
    private Mapa mapa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getxCoord() { return xCoord; }
    public void setxCoord(int xCoord) { this.xCoord = xCoord; }

    public int getyCoord() { return yCoord; }
    public void setyCoord(int yCoord) { this.yCoord = yCoord; }

    public TipoCelda getTipo() { return tipo; }
    public void setTipo(TipoCelda tipo) { this.tipo = tipo; }

    public Mapa getMapa() { return mapa; }
    public void setMapa(Mapa mapa) { this.mapa = mapa; }
}
