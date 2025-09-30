package com.tutorial.categorysservice.repository;

import com.tutorial.categorysservice.entity.LateArrivalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LateArrivalsRepository extends JpaRepository<LateArrivalsEntity, Long> {
    public List<LateArrivalsEntity> findByRut(String rut);

    @Query(value = "SELECT * FROM late_arrivals WHERE late_arrivals.rut = :rut AND late_arrivals.year = :year AND late_arrivals.month = :month", nativeQuery = true)
    List<LateArrivalsEntity> getLateArrivalsByRutYearMonth(@Param("rut") String rut, @Param("year") int year, @Param("month") int month);

}