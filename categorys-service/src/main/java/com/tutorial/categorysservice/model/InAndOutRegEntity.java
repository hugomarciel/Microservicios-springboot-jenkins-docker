package com.tutorial.categorysservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InAndOutRegEntity {

    private String rutEmployee;
    private LocalDate date;   // Fecha del registro
    private LocalTime time;   // Hora de entrada/salida


}