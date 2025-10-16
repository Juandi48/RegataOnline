package co.edu.javeriana.regata.web.dto;

public class MovimientoDTO {
    private int deltaVX;
    private int deltaVY;

    public MovimientoDTO() {}

    public int getDeltaVX() { return deltaVX; }
    public void setDeltaVX(int deltaVX) { this.deltaVX = deltaVX; }
    public int getDeltaVY() { return deltaVY; }
    public void setDeltaVY(int deltaVY) { this.deltaVY = deltaVY; }
}
