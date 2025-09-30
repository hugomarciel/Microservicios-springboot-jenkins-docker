package com.tutorial.categorysservice.service;

import com.tutorial.categorysservice.entity.EmployeeEntity;
import com.tutorial.categorysservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public ArrayList<EmployeeEntity> getEmployees(){
        return (ArrayList<EmployeeEntity>) employeeRepository.findAll();
    }

    public EmployeeEntity saveEmployee(EmployeeEntity employee){
        return employeeRepository.save(employee);
    }

    public EmployeeEntity getEmployeeById(Long id){
        return employeeRepository.findById(id).get();
    }

    public EmployeeEntity getEmployeeByRut(String rut){
        return employeeRepository.findByRut(rut);
    }

    public EmployeeEntity updateEmployee(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(Long id) throws Exception {
        try{
            employeeRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
}