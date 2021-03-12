package com.testyris.testyris.service;

//import com.testyris.testyris.helper.GeometryHelper;
import com.testyris.testyris.helper.GeometryHelper;
import com.testyris.testyris.model.CategoryEntity;
import com.testyris.testyris.model.LocationEntity;
import com.testyris.testyris.model.LocationInformationEntity;
import com.testyris.testyris.repository.LocationInformationRepository;
import com.testyris.testyris.repository.LocationRepository;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;

import static com.testyris.testyris.helper.GeometryHelper.convertGeoJsonToJtsGeometry;


@Service
public class LocationService {

    private LocationRepository locationRepository;
    private LocationInformationRepository locationInformationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, LocationInformationRepository locationInformationRepository) {
        this.locationRepository = locationRepository;
        this.locationInformationRepository = locationInformationRepository;
    }

    public Long saveLocation(Feature feature) {
        LocationEntity locationEntity = convertFeatureToEntity(feature);
        locationRepository.save(locationEntity);
        return locationEntity.getId();
    }

    @Transactional
    public long deleteAllLocationForACategory(Integer idCategory) {
        return locationRepository.deleteByCategoryId(idCategory);
    }

    public Boolean updateLocation(Long id, LocationEntity locationEntity) {
        Boolean result = false;
        LocationEntity locationToUpdate = locationRepository.findById(id).orElse(null);
        if(locationToUpdate != null){
            locationToUpdate.setName(locationEntity.getName());
            locationToUpdate.setGeometry(locationEntity.getGeometry());
            locationRepository.save(locationToUpdate);
            result = true;
        }
        return result;
    }

    public FeatureCollection findAllLocations() {
        List<LocationEntity> locationEntityList = locationRepository.findAll();
        Feature[] features = mapEntityListToFeatures(locationEntityList);
        return new FeatureCollection(features);
    }

    public FeatureCollection findLocationsByCategoryId(Integer id) {
        List<LocationEntity> locationEntityList = locationRepository.findByCategoryId(id);
        Feature[] features = mapEntityListToFeatures(locationEntityList);
        return new FeatureCollection(features);
    }


    public Integer deleteLocations(Long id) {
        locationRepository.deleteById(id);
        return id.intValue();
    }

    public boolean saveLocationInformation(LocationInformationEntity locationInformationEntity) {
        Boolean result = false;
        List<LocationInformationEntity> locationToUpdate = locationInformationRepository.findByLocationId(locationInformationEntity.getLocationId());
        if(locationToUpdate != null){
            if(locationToUpdate.size() != 0) {
                locationToUpdate.get(0).setCommentary(locationInformationEntity.getCommentary());
                locationInformationRepository.save(locationToUpdate.get(0));
                result = true;
            }
        }
        if(!result){
            locationInformationRepository.save(locationInformationEntity);
            result = true;
        }
        return result;
    }

    public LocationInformationEntity getALocationInformationForId(Long id) {
        List<LocationInformationEntity> locationEntityList = locationInformationRepository.findByLocationId(id);
        if(locationEntityList != null){
            if(locationEntityList.size() != 0) {
                return locationEntityList.get(0);
            }
        }
        return null;
    }



    private Feature[] mapEntityListToFeatures(List<LocationEntity> locationEntityList) {
        return locationEntityList.stream()
                .map(this::convertEntityToFeature)
                .toArray(Feature[]::new);
    }

    public LocationEntity convertFeatureToEntity(Feature feature) {
        LocationEntity entity = new LocationEntity();
        Map<String, Object> propertiesList = feature.getProperties();
        Arrays.stream(LocationEntity.class.getDeclaredFields())
                .filter(i -> !i.isSynthetic())
                .forEach(i -> {
                    try {
                        Field f = LocationEntity.class.getDeclaredField(i.getName());
                        f.setAccessible(true);
                        f.set(entity, propertiesList.getOrDefault(i.getName(), null));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        //log.warn(e.getMessage());
                    }
                });
        entity.setGeometry(convertGeoJsonToJtsGeometry(feature.getGeometry()));
        return entity;
    }

    public Feature convertEntityToFeature(LocationEntity entity) {
        Long id = entity.getId();
        org.wololo.geojson.Geometry geometry = GeometryHelper.convertJtsGeometryToGeoJson(entity.getGeometry());

        Map<String, Object> properties = new HashMap<>();
        Arrays.stream(LocationEntity.class.getDeclaredFields())
                .filter(i -> !i.isSynthetic())
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.getType() != Geometry.class && !field.getName().equals("id")) {
                            properties.put(field.getName(), field.get(entity));
                        }
                    } catch (IllegalAccessException e) {
                        //log.warn(e.getMessage());
                    }
                });

        return new Feature(id, geometry, properties);
    }



}