package com.testyris.testyris.controller;

import com.testyris.testyris.model.*;
import com.testyris.testyris.repository.LocationInformationRepository;
import com.testyris.testyris.repository.LocationRepository;
import com.testyris.testyris.service.CategoryService;
import com.testyris.testyris.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
public class VectorController {

    private LocationService locationService;
    private CategoryService categoryService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    public VectorController(LocationService locationService, CategoryService categoryService) {
        this.locationService = locationService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value="/Vector", method= RequestMethod.GET)
    public String listeVector() {
        Iterable<LocationEntity> produits = locationRepository.findAll();
        return "Un exemple de produit";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public ResponseEntity<FeatureCollection> getAllLocations() {
        return new ResponseEntity<>(locationService.findAllLocations(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/locations", method= RequestMethod.POST)
    public ResponseEntity<Long> listeVectorAdd(@RequestBody org.wololo.geojson.Feature geoJson) {
        long id = 0;
        LocationEntity locationEntity = locationService.convertFeatureToEntity(geoJson);
        if(categoryService.getCategoryById(locationEntity.getCategoryId().longValue()) != null){
            id = locationService.saveLocation(geoJson);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/locations", method= RequestMethod.PUT)
    public ResponseEntity<Boolean> updateLocation(@RequestBody org.wololo.geojson.Feature geoJson) {
        boolean result = false;
        LocationEntity locationEntity = locationService.convertFeatureToEntity(geoJson);
        Integer id = (Integer) geoJson.getId();
        if(categoryService.getCategoryById(locationEntity.getCategoryId().longValue()) != null && id != null){
            result = locationService.updateLocation(id.longValue() ,locationEntity);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/locations", method= RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteLocation(@RequestBody Integer id) {
        int result = locationService.deleteLocations(id.longValue());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/data", method= RequestMethod.GET)
    public ResponseEntity<DataManager> getData() {

        List<CategoryEntity> list = categoryService.getAllCategory();
        DataManager dataManager = new DataManager(list);
        return new ResponseEntity<>(dataManager, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/categoriesAndGeoData", method= RequestMethod.GET)
    public ResponseEntity<List<CategoryData>> getCategoriesAndGeoData() {
        List<CategoryData> result = new ArrayList<>();
        List<CategoryEntity> categories = categoryService.getAllCategory();
        for (CategoryEntity category : categories) {
            System.out.print(category);
            FeatureCollection feature = locationService.findLocationsByCategoryId(category.getId().intValue());
            if(feature != null){
                result.add(new CategoryData(category, feature));
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/category", method= RequestMethod.POST)
    public ResponseEntity<Long> addCategory(@RequestBody CategoryEntity category) {
        Long id = categoryService.saveCategory(category);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/category", method= RequestMethod.DELETE)
    public ResponseEntity<Integer> deleteCategory(@RequestBody Integer id) {
        long deletedRecords = locationService.deleteAllLocationForACategory(id);
        Integer result = categoryService.deleteCategory(id.longValue());
        System.out.println(deletedRecords);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/category", method= RequestMethod.PUT)
    public ResponseEntity<Boolean> updateCategory(@RequestBody CategoryEntity category) {
        Boolean result = categoryService.updateCategory(category);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/locationInformation", method= RequestMethod.POST)
    public ResponseEntity<Boolean> addOrUpdateLocationInformation(@RequestBody LocationInformationEntity locationInformation) {
        Boolean result = locationService.saveLocationInformation(locationInformation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/locationInformation", method= RequestMethod.GET)
    public ResponseEntity<LocationAdditionalInformation> getLocationInformation(@RequestParam long id) {
        LocationAdditionalInformation result = new LocationAdditionalInformation(id);
        LocationInformationEntity commentary = locationService.getALocationInformationForId(id);
        if(commentary != null){
            result.setCommentary(commentary.getCommentary());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}