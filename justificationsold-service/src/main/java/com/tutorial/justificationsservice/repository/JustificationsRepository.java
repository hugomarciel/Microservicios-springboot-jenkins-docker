package com.tutorial.justificationsservice.repository;

import com.tutorial.justificationsservice.entity.JustificationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JustificationsRepository extends JpaRepository<JustificationsEntity, Long>  {

}
