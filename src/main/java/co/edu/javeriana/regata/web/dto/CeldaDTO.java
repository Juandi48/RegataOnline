package co.edu.javeriana.regata.web.dto;

public class CeldaDTO {
    private int x;
    private int y;
    private String tipo; // "AGUA", "PARED", "PARTIDA", "META"

    public CeldaDTO() {}

    public CeldaDTO(int x, int y, String tipo) {
        this.x = x; this.y = y; this.tipo = tipo;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
