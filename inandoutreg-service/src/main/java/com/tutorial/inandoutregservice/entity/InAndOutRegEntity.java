package com.tutorial.inandoutregservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "inOutReg")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class InAndOutRegEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rutEmployee;
    private LocalDate date;   // Fecha del registro
    private LocalTime time;   // Hora de entrada/salida


}