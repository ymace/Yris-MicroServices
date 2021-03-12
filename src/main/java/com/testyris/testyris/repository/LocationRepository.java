package com.testyris.testyris.repository;

import com.testyris.testyris.model.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    long deleteByCategoryId(Integer categoryId);
    List<LocationEntity> findByCategoryId(Integer categoryId);
}

