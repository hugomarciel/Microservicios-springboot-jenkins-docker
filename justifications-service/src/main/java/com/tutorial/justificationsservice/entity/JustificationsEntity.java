package com.tutorial.justificationsservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "justifications")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class JustificationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rutEmployee;
    private LocalDate date;
    private String motivation;
    private byte[] document;
}
