package com.tutorial.categorysservice.service;
import com.tutorial.categorysservice.model.JustificationsEntity;
import com.tutorial.categorysservice.entity.LateArrivalsEntity;
import com.tutorial.categorysservice.repository.LateArrivalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountsService {

    @Autowired
    private RestTemplate restTemplate;  // Usar el RestTemplate inyectado
    @Autowired
    private LateArrivalsRepository lateArrivalsRepository;

    public double calculateLateArrivalDiscount(String rut, int year, int month, double grossSalary) {
        // Obtener los minutos de atraso del empleado para el mes y año
        List<LateArrivalsEntity> lateArrivals = lateArrivalsRepository.getLateArrivalsByRutYearMonth(rut, year, month);
        int totalLateMinutes = lateArrivals.stream().mapToInt(LateArrivalsEntity::getNumLateMinutes).sum();

        // Llamada a justifications-service
        String url = "http://justifications-service/api/v1/justifications/";
        ResponseEntity<List<JustificationsEntity>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<JustificationsEntity>>() {}
        );

        List<JustificationsEntity> justifications = response.getBody();

        // Lógica para calcular descuentos
        boolean hasJustification = justifications != null && justifications.stream()
                .anyMatch(justification -> justification.getRutEmployee().equals(rut) &&
                        justification.getDate().getYear() == year &&
                        justification.getDate().getMonthValue() == month);

        if (hasJustification && totalLateMinutes > 70) {
            return 0; // No hay descuento si tiene justificativo
        } else if (totalLateMinutes > 70) {
            return grossSalary; // Descuento completo por inasistencia
        } else if (totalLateMinutes > 45) {
            return grossSalary * 0.06; // 6% de descuento
        } else if (totalLateMinutes > 25) {
            return grossSalary * 0.03; // 3% de descuento
        } else if (totalLateMinutes > 10) {
            return grossSalary * 0.01; // 1% de descuento
        }

        return 0; // No hay descuento si el atraso es menor a 10 minutos
    }
}
