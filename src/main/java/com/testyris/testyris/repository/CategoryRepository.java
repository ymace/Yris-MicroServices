package com.testyris.testyris.repository;

import com.testyris.testyris.model.CategoryEntity;
import com.testyris.testyris.model.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
