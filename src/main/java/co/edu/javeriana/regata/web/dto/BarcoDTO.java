package co.edu.javeriana.regata.web.dto;

public class BarcoDTO {
    private Long id;
    private Long modeloId;
    private Long jugadorId;
    private int velX;
    private int velY;
    private int posX;
    private int posY;
    private String colorHex;
    private String nombreModelo;
    private String nombreJugador;

    public BarcoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getModeloId() { return modeloId; }
    public void setModeloId(Long modeloId) { this.modeloId = modeloId; }
    public Long getJugadorId() { return jugadorId; }
    public void setJugadorId(Long jugadorId) { this.jugadorId = jugadorId; }
    public int getVelX() { return velX; }
    public void setVelX(int velX) { this.velX = velX; }
    public int getVelY() { return velY; }
    public void setVelY(int velY) { this.velY = velY; }
    public int getPosX() { return posX; }
    public void setPosX(int posX) { this.posX = posX; }
    public int getPosY() { return posY; }
    public void setPosY(int posY) { this.posY = posY; }
    public String getColorHex() { return colorHex; }
    public void setColorHex(String colorHex) { this.colorHex = colorHex; }
    public String getNombreModelo() { return nombreModelo; }
    public void setNombreModelo(String nombreModelo) { this.nombreModelo = nombreModelo; }
    public String getNombreJugador() { return nombreJugador; }
    public void setNombreJugador(String nombreJugador) { this.nombreJugador = nombreJugador; }
}
