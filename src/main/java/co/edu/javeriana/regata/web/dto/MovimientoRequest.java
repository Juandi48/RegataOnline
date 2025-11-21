package co.edu.javeriana.regata.web.dto;

public class MovimientoRequest {

    private Long barcoId;
    private int deltaVx;
    private int deltaVy;

    public MovimientoRequest() {
    }

    public Long getBarcoId() {
        return barcoId;
    }

    public void setBarcoId(Long barcoId) {
        this.barcoId = barcoId;
    }

    public int getDeltaVx() {
        return deltaVx;
    }

    public void setDeltaVx(int deltaVx) {
        this.deltaVx = deltaVx;
    }

    public int getDeltaVy() {
        return deltaVy;
    }

    public void setDeltaVy(int deltaVy) {
        this.deltaVy = deltaVy;
    }
}
