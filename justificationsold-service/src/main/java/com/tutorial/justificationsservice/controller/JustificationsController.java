package com.tutorial.justificationsservice.controller;

import com.tutorial.justificationsservice.entity.JustificationsEntity;
import com.tutorial.justificationsservice.service.JustificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/justifications")
//@CrossOrigin("*")
public class JustificationsController {
    @Autowired
    JustificationsService justificationsService;

    @GetMapping("/")
    public ResponseEntity<List<JustificationsEntity>> listJustifications() {
        List<JustificationsEntity> justifications = justificationsService.getJustifications();
        return ResponseEntity.ok(justifications);
    }

    @PostMapping("/")
    public ResponseEntity<JustificationsEntity> saveJustification(@RequestBody JustificationsEntity justification) {
        JustificationsEntity justificationNew = justificationsService.saveJustification(justification);
        return ResponseEntity.ok(justificationNew);
    }
}