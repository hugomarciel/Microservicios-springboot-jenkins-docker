package com.tutorial.categorysservice.service;

import com.tutorial.categorysservice.entity.LateArrivalsEntity;
import com.tutorial.categorysservice.repository.LateArrivalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.tutorial.categorysservice.model.InAndOutRegEntity;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LateArrivalsService {

    @Autowired
    LateArrivalsRepository lateArrivalsRepository;

    @Autowired
    RestTemplate restTemplate;
    //InAndOutRegService inAndOutRegService;

    // Calcular y guardar los minutos de atraso
    public void calculateAndSaveLateArrivals(int month, int year) {
        // Definir el horario de inicio de la jornada laboral
        LocalTime workStartTime = LocalTime.of(8, 0);

        // Realizar la solicitud GET utilizando RestTemplate y ParameterizedTypeReference
        String url = "http://inandoutreg-service/api/v1/inandout/getall/" + year + "/" + month;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<InAndOutRegEntity>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InAndOutRegEntity>>() {}
        );

        List<InAndOutRegEntity> inAndOutRegs = response.getBody();
        System.out.println("Cantidad de registros obtenidos: " + inAndOutRegs.size());

        // Si la lista está vacía, imprimir un mensaje de advertencia
        if (inAndOutRegs.isEmpty()) {
            System.out.println("No se encontraron registros para el mes y año indicados.");
            return;
        }

        // Agrupar por RUT y fecha
        Map<String, Map<LocalDate, List<InAndOutRegEntity>>> recordsByEmployeeAndDate = inAndOutRegs.stream()
                .collect(Collectors.groupingBy(InAndOutRegEntity::getRutEmployee,
                        Collectors.groupingBy(InAndOutRegEntity::getDate))); // Usar el atributo date
        System.out.println("Cantidad de empleados: " + recordsByEmployeeAndDate.size());

        // Calcular minutos de atraso para cada empleado
        for (String rut : recordsByEmployeeAndDate.keySet()) {
            Map<LocalDate, List<InAndOutRegEntity>> recordsByDate = recordsByEmployeeAndDate.get(rut);
            System.out.println("Procesando empleado con RUT: " + rut + ", cantidad de días con registros: " + recordsByDate.size());

            int totalLateMinutes = 0;

            for (Map.Entry<LocalDate, List<InAndOutRegEntity>> entry : recordsByDate.entrySet()) {
                List<InAndOutRegEntity> records = entry.getValue();
                System.out.println("Fecha: " + entry.getKey() + ", cantidad de registros para este día: " + records.size());

                // Verificar que hay al menos un registro de entrada
                if (!records.isEmpty()) {
                    InAndOutRegEntity firstRecord = records.get(0); // Primer registro asumido como entrada

                    // Calcular los minutos de atraso
                    if (firstRecord.getTime().isAfter(workStartTime)) {
                        int lateMinutes = (int) Duration.between(workStartTime, firstRecord.getTime()).toMinutes();
                        System.out.println("Atraso para el día " + entry.getKey() + ": " + lateMinutes + " minutos");
                        totalLateMinutes += lateMinutes;
                    }
                }
            }

            System.out.println("Total minutos de atraso para empleado con RUT: " + rut + ": " + totalLateMinutes);

            // Guardar los minutos de atraso calculados
            LateArrivalsEntity lateArrival = new LateArrivalsEntity();
            lateArrival.setRut(rut);
            lateArrival.setMonth(month);
            lateArrival.setYear(year);
            lateArrival.setNumLateMinutes(totalLateMinutes);
            System.out.println("Guardando atraso para empleado con RUT: " + rut);
            lateArrivalsRepository.save(lateArrival);
            System.out.println("Guardado exitoso para empleado con RUT: " + rut);
        }
    }
}
