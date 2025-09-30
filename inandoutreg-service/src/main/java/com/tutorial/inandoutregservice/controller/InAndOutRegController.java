package com.tutorial.inandoutregservice.controller;

import com.tutorial.inandoutregservice.service.InAndOutRegService;
import com.tutorial.inandoutregservice.entity.InAndOutRegEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inandout")
//@CrossOrigin("*")
public class InAndOutRegController {

    @Autowired
    private InAndOutRegService inAndOutRegService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            inAndOutRegService.importData(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall/{year}/{month}")
    public ResponseEntity<List<InAndOutRegEntity>> getAll(@PathVariable("month") int month, @PathVariable("year") int year) {
        List<InAndOutRegEntity> regs = inAndOutRegService.getAllInAndOutRegsByMonthAndYear( month,  year);
        if(regs.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(regs);
    }
}
