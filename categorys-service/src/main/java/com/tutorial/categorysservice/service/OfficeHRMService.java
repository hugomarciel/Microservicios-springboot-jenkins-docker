package com.tutorial.categorysservice.service;
import com.tutorial.categorysservice.entity.EmployeeEntity;
import com.tutorial.categorysservice.repository.ExtraHoursRepository;
import com.tutorial.categorysservice.entity.CategorysEntity;
import com.tutorial.categorysservice.entity.ExtraHoursEntity;
import com.tutorial.categorysservice.repository.CategorysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service


public class OfficeHRMService {
    //@Autowired
    //CategoryRepository categoryRepository;

    @Autowired
    ExtraHoursRepository extraHoursRepository;
    @Autowired
    private CategorysRepository categorysRepository;
    @Autowired
    private RestTemplate restTemplate;

    //@Autowired
    //AutorizationsService autorizationsService;

    //@Autowired
    //CategorysEntity categorysEntity;


    // calcula el salario anual del employee
    // public int getAnnualSalary(EmployeeEntity employee) {
    //  int annualSalary = 0;
    //annualSalary = employee.getSalary() * 12;
    //return annualSalary;
    //}

    // Calcula bonificacion según monto del sueldo
    // < 2.000: 10% del monto de su sueldo; caso contrario 20%
    public int getSalaryBonus(EmployeeEntity employee) {
        int salaryBonus = 0;

        if(employee.getSalary() < 2000){
            salaryBonus = Math.round(employee.getSalary() * 0.1f);
        }else{
            salaryBonus = Math.round(employee.getSalary() * 0.2f);
        }

        return salaryBonus;
    }

    // Calcula bonificacion según numero de Hijos
    // < 3: 5% del monto de su sueldo por cada hijo; caso contrario 15%
    public int getChildrenBonus(EmployeeEntity employee) {
        int childrenBonus = 0;

        if(employee.getChildren() < 3){
            childrenBonus = Math.round(employee.getSalary() * 0.05f) * employee.getChildren();
        }else{
            childrenBonus = Math.round(employee.getSalary() * 0.15f) * employee.getChildren();
        }

        return childrenBonus;
    }

    //Calcula bonificacion por numero de horas extras
    //Categoria "A": le corresponde 100 dolares
    //Categoria "B": le corresponde 60 dolares
    //Categoria "C": le corresponde 20 dolares
    public int getExtraHoursBonus(EmployeeEntity employee, int numExtraHours) {
        int extraHoursBonus = 0;

        if(employee.getCategory() == "A"){
            extraHoursBonus = numExtraHours * 100;
        }else{
            if(employee.getCategory() == "B") {
                extraHoursBonus = numExtraHours * 60;
            } else {
                extraHoursBonus = numExtraHours * 20;
            }
        }

        return extraHoursBonus;
    }
    public int calculateYearsOfService(EmployeeEntity employee) {
        if (employee.getEntryDate() == null) {
            return 0; // Si no hay fecha de entrada, retorna 0
        }

        LocalDate entryDate = employee.getEntryDate();
        LocalDate currentDate = LocalDate.now(); // Fecha actual

        // Calcula el periodo entre las dos fechas
        Period period = Period.between(entryDate, currentDate);
        return period.getYears(); // Retorna solo los años de servicio
    }
    // Comentario jueves 17 de octubre
    public int getMonthlySalary(EmployeeEntity employee) {
        CategorysEntity categoryEntity = categorysRepository.findSalaryByCategory(employee.getCategory());
        if (categoryEntity != null) {
            // Imprimir la categoría encontrada y su sueldo
            System.out.println("Categoría encontrada: " + categoryEntity.getCategory() + ", Sueldo: " + categoryEntity.getSalary());

            return categoryEntity.getSalary(); // Retorna el sueldo fijo mensual de la categoría
        }
        return 0; // Retorna 0 si no se encuentra la categoría
    }

    // Método para calcular el bono por años de servicio
    public double calculateYearsServiceBonus(EmployeeEntity employee) {
        int yearsOfService = calculateYearsOfService(employee);
        double monthlySalary = getMonthlySalary(employee); // Sueldo fijo mensual

        if (yearsOfService < 5) {
            return 0; // 0% bono
        } else if (yearsOfService < 10) {
            return monthlySalary * 0.05; // 5% bono
        } else if (yearsOfService < 15) {
            return monthlySalary * 0.08; // 8% bono
        } else if (yearsOfService < 20) {
            return monthlySalary * 0.11; // 11% bono
        } else if (yearsOfService < 25) {
            return monthlySalary * 0.14; // 14% bono
        } else {
            return monthlySalary * 0.17; // 17% bono
        }
    }
    // Método para calcular el monto a pagar por horas extras autorizadas
    public double calculateExtraHoursPayment(EmployeeEntity employee, int year, int month) {
        // 1. Obtener el número de horas extras realizadas
        List<ExtraHoursEntity> extraHoursList = extraHoursRepository.getExtraHoursByRutYearMonth(employee.getRut(), year, month);

        // Calcular el total de horas extras
        int totalExtraHours = extraHoursList.stream()
                .mapToInt(ExtraHoursEntity::getNumExtraHours)
                .sum();

        // 2. Verificar si las horas extras están autorizadas usando RestTemplate
        String url = String.format("http://autorizations-service/autorizations/authorizedeh/" + employee.getRut() + "/"  + year + "/" + month);
        int authorizedExtraHours = restTemplate.getForObject(url, Integer.class);

        // 3. Determinar las horas a pagar (solo las autorizadas)
        int payableExtraHours = Math.min(totalExtraHours, authorizedExtraHours);

        // 4. Obtener la tarifa de horas extras según la categoría
        CategorysEntity categoryEntity = categorysRepository.findSalaryByCategory(employee.getCategory());
        if (categoryEntity == null) {
            return 0; // Si no hay categoría, no se paga nada
        }
        double extraHourRate = categoryEntity.getExtraHourRate();

        // 5. Calcular el monto a pagar por las horas extras autorizadas
        return payableExtraHours * extraHourRate;
    }


}
