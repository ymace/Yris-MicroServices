package com.testyris.testyris.model;

import org.wololo.geojson.FeatureCollection;

import java.util.List;
import java.util.Map;

public class CategoryData {
    CategoryEntity category;
    FeatureCollection geoData;

    public CategoryData(CategoryEntity category, FeatureCollection geoData){
        this.category = category;
        this.geoData = geoData;
    }


    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public FeatureCollection getGeoData() {
        return geoData;
    }

    public void setGeoData(FeatureCollection geoData) {
        this.geoData = geoData;
    }
}
