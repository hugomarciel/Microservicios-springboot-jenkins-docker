package com.tutorial.categorysservice.repository;

import com.tutorial.categorysservice.entity.ExtraHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraHoursRepository extends JpaRepository<ExtraHoursEntity, Long> {
    public List<ExtraHoursEntity> findByRut(String rut);
    @Query(value = "SELECT * FROM extra_hours WHERE extra_hours.rut = :rut AND extra_hours.year = :year AND extra_hours.month = :month", nativeQuery = true)
    List<ExtraHoursEntity> getExtraHoursByRutYearMonth(@Param("rut") String rut, @Param("year") int year, @Param("month") int month);



}