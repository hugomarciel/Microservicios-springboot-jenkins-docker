package com.tutorial.categorysservice.service;

import com.tutorial.categorysservice.entity.EmployeeEntity;
import com.tutorial.categorysservice.entity.PaycheckEntity;
import com.tutorial.categorysservice.repository.PaycheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaycheckService {
    @Autowired
    PaycheckRepository paycheckRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    OfficeHRMService officeHRMService;

    @Autowired
    ExtraHoursService extraHoursService;

    @Autowired
    DiscountsService discountsService;

    //@Autowired
    //CategoryRepository categoryRepository;

    public ArrayList<PaycheckEntity> getPaychecks() {
        return (ArrayList<PaycheckEntity>) paycheckRepository.findAll();
    }

    public PaycheckEntity savePaycheck(PaycheckEntity paycheck) {
        return paycheckRepository.save(paycheck);
    }

    public ArrayList<PaycheckEntity> getPaychecksByYearMonth(Integer year, Integer month) {
        return (ArrayList<PaycheckEntity>) paycheckRepository.getPaychecksByYearMonth(year, month);
    }

    public Boolean calculatePaychecks(int year, int month) {
        List<EmployeeEntity> employees = employeeService.getEmployees();

        if (employees.isEmpty()) {
            // Manejar caso de lista vacía
            return false;
        }

        for (EmployeeEntity employee : employees) {
            try {
                PaycheckEntity paycheck = createPaycheckForEmployee(employee, year, month);
                paycheckRepository.save(paycheck);
            } catch (Exception e) {
                // Manejo de errores (registrar, lanzar una excepción personalizada, etc.)
                System.err.println("Error al procesar el paycheck para el empleado: " + employee.getRut());
                e.printStackTrace();
            }
        }

        return true;
    }

    private PaycheckEntity createPaycheckForEmployee(EmployeeEntity employee, int year, int month) {
        PaycheckEntity paycheck = new PaycheckEntity();
        paycheck.setRut(employee.getRut());
        paycheck.setName(employee.getNames());
        paycheck.setLastName(employee.getLastNames());
        paycheck.setCategory(employee.getCategory());

        // Obtener el sueldo mensual según la categoría
        int monthlySalary = officeHRMService.getMonthlySalary(employee);
        paycheck.setMonthSalary(monthlySalary); // Establecer sueldo mensual

        // Calcular años de servicio
        int servicesYears = officeHRMService.calculateYearsOfService(employee);
        paycheck.setServicesYears(servicesYears);

        // Calcular bonificación por años de servicio
        double yearsServicesBonus = officeHRMService.calculateYearsServiceBonus(employee);
        paycheck.setServicesYearsBonus(yearsServicesBonus);

        // Calcular horas extras
        int extraHoursBonus = extraHoursService.getTotalExtraHoursAmountByRutYearMonth(employee.getRut(), year, month);
        paycheck.setExtraHoursBonus(extraHoursBonus);

        // Calcular descuentos por atrasos
        double discounts = discountsService.calculateLateArrivalDiscount(employee.getRut(), year, month, monthlySalary);
        paycheck.setDiscounts(discounts);

        // Calcular salario bruto
        double grossSalary = monthlySalary + yearsServicesBonus + extraHoursBonus;
        paycheck.setGrossSalary(grossSalary);

        // Calcular salario menos descuentos
        double lessDiscountSalary = grossSalary - discounts;

        // Calcular aportes
        double forecastQuote = calculateForecastQuote(lessDiscountSalary);
        paycheck.setForecastQuote(forecastQuote);
        double healthQuote = calculateHealthQuote(lessDiscountSalary);
        paycheck.setHealthQuote(healthQuote);

        // Calcular salario total
        paycheck.setTotalSalary(lessDiscountSalary - forecastQuote - healthQuote);

        // Establecer mes y año
        paycheck.setMonth(month);
        paycheck.setYear(year);

        return paycheck;
    }

    private double calculateForecastQuote(double salary) {
        return salary * 0.10; // 10% de previsión
    }

    private double calculateHealthQuote(double salary) {
        return salary * 0.08; // 8% de salud
    }
}
