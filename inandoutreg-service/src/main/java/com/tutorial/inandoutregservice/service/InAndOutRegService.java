package com.tutorial.inandoutregservice.service;

import com.tutorial.inandoutregservice.entity.InAndOutRegEntity;
import com.tutorial.inandoutregservice.repository.InAndOutRegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Service
public class InAndOutRegService {

    @Autowired
    private InAndOutRegRepository inAndOutRegRepository;

    public List<InAndOutRegEntity> getAllInAndOutRegsByMonthAndYear(int month, int year) {
        return inAndOutRegRepository.findByMonthAndYear(month, year);
    }

    public void importData(MultipartFile file) {
        List<InAndOutRegEntity> inAndOutRegs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                LocalDate date = LocalDate.parse(parts[0], dateFormatter);
                LocalTime time = LocalTime.parse(parts[1], timeFormatter);
                String rutEmployee = parts[2];

                InAndOutRegEntity reg = new InAndOutRegEntity();
                reg.setDate(date);
                reg.setTime(time);
                reg.setRutEmployee(rutEmployee);

                inAndOutRegs.add(reg);
            }

            inAndOutRegRepository.saveAll(inAndOutRegs);

        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo: " + e.getMessage());
        }
    }
}
