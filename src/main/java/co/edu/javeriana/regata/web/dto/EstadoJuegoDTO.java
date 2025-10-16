package co.edu.javeriana.regata.web.dto;

public class EstadoJuegoDTO {
    private int turno;
    private String estado; // EN_CURSO | GANADO | DESTRUIDO
    private MapaDTO mapa;
    private BarcoDTO barco;

    public EstadoJuegoDTO() {}

    public int getTurno() { return turno; }
    public void setTurno(int turno) { this.turno = turno; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public MapaDTO getMapa() { return mapa; }
    public void setMapa(MapaDTO mapa) { this.mapa = mapa; }
    public BarcoDTO getBarco() { return barco; }
    public void setBarco(BarcoDTO barco) { this.barco = barco; }
}
