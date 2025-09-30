package com.tutorial.autorizationsservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "authorizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorizationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rutEmployee;   // Rut del empleado que solicita la autorización
    private LocalDate date;       // Fecha de la autorización
    private String reason;        // Razón de la autorización
    private int AuthorizedHours;
    @Column(nullable = true)//
    private byte[] document;      // Documento de autorización firmado o anexo
}
