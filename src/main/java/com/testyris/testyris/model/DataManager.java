package com.testyris.testyris.model;

import java.util.List;

public class DataManager {

    private List<CategoryEntity> categories;

    public DataManager(List<CategoryEntity> categories){
        this.categories = categories;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }
}
