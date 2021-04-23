package isi.dan.laboratorios.danmsusuarios.service;

public interface RiesgoService {
    public Boolean reporteVerazPositivo(String cuit);
    public Boolean reporteBCRAPositivo(String cuit);
    public Boolean reporteAFIPPositivo(String cuit);
}