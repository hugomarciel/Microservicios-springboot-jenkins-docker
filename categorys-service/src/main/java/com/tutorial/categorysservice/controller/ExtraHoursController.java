package com.tutorial.categorysservice.controller;
import  com.tutorial.categorysservice.service.ExtraHoursService;
import com.tutorial.categorysservice.entity.ExtraHoursEntity;
import com.tutorial.categorysservice.service.CategorysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/extraHours")
//@CrossOrigin("*")
public class ExtraHoursController {
    @Autowired
    ExtraHoursService extraHoursService;


    @GetMapping("/")
    public ResponseEntity<List<ExtraHoursEntity>> listExtraHours() {
        List<ExtraHoursEntity> extraHours = extraHoursService.getExtraHours();
        return ResponseEntity.ok(extraHours);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtraHoursEntity> getExtraHourById(@PathVariable Long id) {
        ExtraHoursEntity extraHour = extraHoursService.getExtraHourById(id);
        return ResponseEntity.ok(extraHour);
    }

    @PostMapping("/")
    public ResponseEntity<ExtraHoursEntity> saveExtraHours(@RequestBody ExtraHoursEntity extraHour) {
        ExtraHoursEntity extraHourNew = extraHoursService.saveExtraHours(extraHour);
        return ResponseEntity.ok(extraHourNew);
    }

    @GetMapping("/{rut}/{year}/{month}")
    public ResponseEntity<List<ExtraHoursEntity>> listExtraHoursByRut(@PathVariable("rut") String rut, @PathVariable("year") int year, @PathVariable("month") int month) {
        List<ExtraHoursEntity> extraHours = extraHoursService.getExtraHoursByRutYearMonth(rut,year,month);
        return ResponseEntity.ok(extraHours);
    }

    @PutMapping("/")
    public ResponseEntity<ExtraHoursEntity> updateExtraHours(@RequestBody ExtraHoursEntity extraHours){
        ExtraHoursEntity extraHoursUpdated = extraHoursService.updateExtraHour(extraHours);
        return ResponseEntity.ok(extraHoursUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteExtraHoursById(@PathVariable Long id) throws Exception {
        var isDeleted = extraHoursService.deleteExtraHour(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/calculate/{year}/{month}")
    public ResponseEntity<Void> calculateExtraHours(@PathVariable int year, @PathVariable int month) {
        extraHoursService.calculateAndSaveExtraHours(month, year);
        return ResponseEntity.ok().build();
    }

}
