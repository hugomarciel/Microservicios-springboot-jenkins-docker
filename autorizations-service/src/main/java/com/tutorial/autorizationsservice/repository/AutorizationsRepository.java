package com.tutorial.autorizationsservice.repository;

import com.tutorial.autorizationsservice.entity.AutorizationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AutorizationsRepository extends JpaRepository<AutorizationsEntity, Long> {
    @Query("SELECT a FROM AutorizationsEntity a WHERE a.rutEmployee = :rut AND YEAR(a.date) = :year AND MONTH(a.date) = :month")
    AutorizationsEntity findByRutYearMonth(@Param("rut") String rut, @Param("year") int year, @Param("month") int month);
}