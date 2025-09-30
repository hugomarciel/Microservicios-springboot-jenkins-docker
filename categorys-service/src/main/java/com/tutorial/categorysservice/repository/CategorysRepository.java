package com.tutorial.categorysservice.repository;

import com.tutorial.categorysservice.entity.CategorysEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorysRepository extends JpaRepository<CategorysEntity, Long> {
    CategorysEntity findSalaryByCategory(String category);
}
