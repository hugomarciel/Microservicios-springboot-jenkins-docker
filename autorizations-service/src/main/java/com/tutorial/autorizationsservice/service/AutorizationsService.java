package com.tutorial.autorizationsservice.service;

import com.tutorial.autorizationsservice.entity.AutorizationsEntity;
import com.tutorial.autorizationsservice.repository.AutorizationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AutorizationsService {
    @Autowired
    AutorizationsRepository autorizationsRepository;

    public ArrayList<AutorizationsEntity> getAutorizations(){
        return (ArrayList<AutorizationsEntity>) autorizationsRepository.findAll();
    }

    public AutorizationsEntity saveAutorizations(AutorizationsEntity authorization){
        return autorizationsRepository.save(authorization);
    }

    // Método para obtener horas extras autorizadas por rut, año y mes
    public int getAuthorizedExtraHours(String rut, int year, int month) {
        // Aquí obtendremos las autorizaciones por rut, año y mes.
        // Puedes ajustar esta lógica de acuerdo con cómo está estructurada la entidad de Autorizaciones
        AutorizationsEntity authorization = autorizationsRepository.findByRutYearMonth(rut, year, month);

        if (authorization != null) {
            return authorization.getAuthorizedHours(); // Si tiene autorizaciones, devolvemos las horas autorizadas
        }

        return 0; // Si no hay autorización, devolvemos 0 horas autorizadas
    }
}
