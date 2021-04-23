package isi.dan.laboratorios.danmsusuarios.exception;

public class RiesgoException extends Exception {
    final public String entidad;

    public RiesgoException(String entidad){
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        return "Riesgo en: "+entidad;
    }
}
