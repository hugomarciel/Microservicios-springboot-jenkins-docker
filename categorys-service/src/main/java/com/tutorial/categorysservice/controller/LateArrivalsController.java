package com.tutorial.categorysservice.controller;

import com.tutorial.categorysservice.entity.LateArrivalsEntity;
import com.tutorial.categorysservice.service.LateArrivalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/latearrivals")
//@CrossOrigin("*")
public class LateArrivalsController {

    @Autowired
    private LateArrivalsService lateArrivalsService;

    @PostMapping("/calculate/{year}/{month}")
    public ResponseEntity<Void> calculateMinuts(@PathVariable int year, @PathVariable int month) {
        // Llamar al servicio para calcular y guardar los minutos de atraso
        lateArrivalsService.calculateAndSaveLateArrivals(month, year);
        return ResponseEntity.ok().build(); // Devuelve un 200 OK si la operación fue exitosa
    }
}