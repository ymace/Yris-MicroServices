package com.testyris.testyris.repository;

import com.testyris.testyris.model.LocationEntity;
import com.testyris.testyris.model.LocationInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationInformationRepository extends JpaRepository<LocationInformationEntity, Long> {
    List<LocationInformationEntity> findByLocationId(Long locationId);
}