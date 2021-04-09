package uy.sample.utils;

public class MarkerInfo {
    private Float longitud;
    private Float latitud;
    private boolean habilitada;


    public MarkerInfo(float longitud, float latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
        this.habilitada = false;
    }

    public MarkerInfo() {
        this.longitud = 0f;
        this.latitud = 0f;
        this.habilitada = false;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }
    
    public boolean isHabilitada() {
        return habilitada;
    }

    public void setHabilitada(boolean habilitada) {
        this.habilitada = habilitada;
    }

    
}
