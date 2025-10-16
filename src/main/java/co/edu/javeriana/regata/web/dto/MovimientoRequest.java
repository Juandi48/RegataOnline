package co.edu.javeriana.regata.web.dto;

public class MovimientoRequest {
    // aceleraciones -1, 0, 1
    public int ax;
    public int ay;

    public MovimientoRequest() {}

    public MovimientoRequest(int ax, int ay) {
        this.ax = ax;
        this.ay = ay;
    }
}
