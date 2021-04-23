package isi.dan.laboratorios.danmsusuarios.service;

import org.springframework.stereotype.Service;

@Service
public class RiesgoServiceImp implements RiesgoService {

    @Override
    public Boolean reporteVerazPositivo(String cuit) {
        return null;
    }

    @Override
    public Boolean reporteBCRAPositivo(String cuit) {
        Integer situacionCrediticia = 1;

        if(situacionCrediticia == 1 || situacionCrediticia == 2) return true;
        return false;
    }

    @Override
    public Boolean reporteAFIPPositivo(String cuit) {
        return null;
    }
    
}
