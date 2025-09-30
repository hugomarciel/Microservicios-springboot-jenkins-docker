package com.tutorial.categorysservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "categorys")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorysEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;


    private String category;  // Nombre de la categoría (A, B, C, etc.)
    private int salary;  // Sueldo fijo mensual según la categoría
    private int extraHourRate;  // Monto a pagar por hora extra según la categoría
}