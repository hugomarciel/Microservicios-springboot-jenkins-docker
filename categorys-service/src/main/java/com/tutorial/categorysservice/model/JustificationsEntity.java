package com.tutorial.categorysservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class JustificationsEntity {

    private String rutEmployee;
    private LocalDate date;
    private String motivation;
    private byte[] document;
}


