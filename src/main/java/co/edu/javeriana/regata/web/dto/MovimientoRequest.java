package co.edu.javeriana.regata.web.dto;

public class MovimientoRequest {

    // ID del barco a mover
    private Long barcoId;

    // aceleración / cambio de velocidad en x e y: -1, 0 o +1
    private int ax;
    private int ay;

    public MovimientoRequest() {
    }

    public Long getBarcoId() {
        return barcoId;
    }

    public void setBarcoId(Long barcoId) {
        this.barcoId = barcoId;
    }

    public int getAx() {
        return ax;
    }

    public void setAx(int ax) {
        this.ax = ax;
    }

    public int getAy() {
        return ay;
    }

    public void setAy(int ay) {
        this.ay = ay;
    }
}
