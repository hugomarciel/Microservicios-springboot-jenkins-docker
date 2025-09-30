package com.tutorial.autorizationsservice.controller;

import com.tutorial.autorizationsservice.entity.AutorizationsEntity;
import com.tutorial.autorizationsservice.service.AutorizationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/authorizations")
//@CrossOrigin("*")
public class AutorizationsController {
    @Autowired
    AutorizationsService autorizationsService;

    @GetMapping("/")
    public ResponseEntity<List<AutorizationsEntity>> listAutorizations() {
        List<AutorizationsEntity> autorizations = autorizationsService.getAutorizations();
        return ResponseEntity.ok(autorizations);
    }

    @PostMapping("/")
    public ResponseEntity<AutorizationsEntity> saveAuthorization(@RequestBody AutorizationsEntity authorization) {
        AutorizationsEntity authorizationNew = autorizationsService.saveAutorizations(authorization);
        return ResponseEntity.ok(authorizationNew);
    }

    @GetMapping("/authorizedeh/{rut}/{year}/{month}")
    public ResponseEntity<Integer> getAuthorizedExtraHours(@RequestParam String rut,
                                                           @RequestParam int year,
                                                           @RequestParam int month) {
        // Llamar al servicio para obtener las horas extra autorizadas
         int authorizationNew = autorizationsService.getAuthorizedExtraHours(rut, year, month);
         return ResponseEntity.ok(authorizationNew);
    }

}
