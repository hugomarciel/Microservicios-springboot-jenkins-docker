package com.tutorial.categorysservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lateArrivals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LateArrivalsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private int month;
    private int year;
    private int numLateMinutes;
}
