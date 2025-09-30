package com.tutorial.categorysservice.service;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.tutorial.categorysservice.entity.ExtraHoursEntity;
import com.tutorial.categorysservice.repository.ExtraHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.tutorial.categorysservice.model.InAndOutRegEntity;
import java.time.Duration;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExtraHoursService {
    @Autowired
    ExtraHoursRepository extraHoursRepository;

    @Autowired
    RestTemplate restTemplate;
    //InAndOutRegService inAndOutRegService;

    public ArrayList<ExtraHoursEntity> getExtraHours(){
        return (ArrayList<ExtraHoursEntity>) extraHoursRepository.findAll();
    }

    public ExtraHoursEntity saveExtraHours(ExtraHoursEntity extraHour){
        return extraHoursRepository.save(extraHour);
    }

    public ExtraHoursEntity getExtraHourById(Long id){
        return extraHoursRepository.findById(id).get();
    }

    public List<ExtraHoursEntity> getExtraHourByRut(String rut){
        return (List<ExtraHoursEntity>) extraHoursRepository.findByRut(rut);
    }

    public ExtraHoursEntity updateExtraHour(ExtraHoursEntity extraHour) {
        return extraHoursRepository.save(extraHour);
    }

    public List<ExtraHoursEntity> getExtraHoursByRutYearMonth(String rut, int year, int month) {
        return (List<ExtraHoursEntity>) extraHoursRepository.getExtraHoursByRutYearMonth(rut, year, month);
    }

    public int getTotalExtraHoursAmountByRutYearMonth(String rut, int year, int month) {
        List<ExtraHoursEntity> extraHours = extraHoursRepository.getExtraHoursByRutYearMonth(rut, year, month);
        int sumExtraHours = 0;
        for (ExtraHoursEntity extraHour : extraHours) {
            sumExtraHours = sumExtraHours + extraHour.getNumExtraHours();
        }
        return sumExtraHours;
    }

    public boolean deleteExtraHour(Long id) throws Exception {
        try{
            extraHoursRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void calculateAndSaveExtraHours(int month, int year) {
        // Definir la jornada laboral
        LocalTime workStartTime = LocalTime.of(8, 0);
        LocalTime workEndTime = LocalTime.of(18, 0);

        // Realizar la solicitud GET utilizando ParameterizedTypeReference
        String url = "http://inandoutreg-service/api/v1/inandout/getall/" + year + "/" + month;

        // Usar restTemplate.exchange() para deserializar correctamente la respuesta
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<InAndOutRegEntity>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InAndOutRegEntity>>() {}
        );

        List<InAndOutRegEntity> inAndOutRegs = response.getBody();

        // Agrupar por rut
        Map<String, List<InAndOutRegEntity>> recordsByEmployee = inAndOutRegs.stream()
                .collect(Collectors.groupingBy(InAndOutRegEntity::getRutEmployee));

        // Calcular horas extras para cada empleado
        for (String rut : recordsByEmployee.keySet()) {
            List<InAndOutRegEntity> records = recordsByEmployee.get(rut);
            int totalExtraHours = 0;

            for (int i = 0; i < records.size(); i += 2) {
                InAndOutRegEntity entry = records.get(i);  // Marca de entrada
                InAndOutRegEntity exit = i + 1 < records.size() ? records.get(i + 1) : null;  // Marca de salida (si existe)

                // Verificar si hay una salida
                if (exit != null) {
                    // Calcular horas extras antes de la jornada
                    if (entry.getTime().isBefore(workStartTime)) {
                        totalExtraHours += Duration.between(entry.getTime(), workStartTime).toMinutes();
                    }

                    // Calcular horas extras después de la jornada
                    if (exit.getTime().isAfter(workEndTime)) {
                        totalExtraHours += Duration.between(workEndTime, exit.getTime()).toMinutes();
                    }
                }
            }

            // Convertir minutos a horas, incluyendo fracciones
            int extraHoursInHours = totalExtraHours / 60;

            // Guardar las horas extras calculadas
            ExtraHoursEntity extraHours = new ExtraHoursEntity();
            extraHours.setRut(rut);
            extraHours.setMonth(month);
            extraHours.setYear(year);
            extraHours.setNumExtraHours(extraHoursInHours);

            extraHoursRepository.save(extraHours);
        }
    }
}
