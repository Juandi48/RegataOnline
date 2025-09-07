package co.edu.javeriana.regata.domain;

public enum TipoCelda {
    AGUA("Agua", "~"), 
    PARED("Pared", "X"), 
    PARTIDA("Partida", "P"), 
    META("Meta", "M");

    private final String nombre;
    private final String simbolo;

    TipoCelda(String nombre, String simbolo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }
    
}

