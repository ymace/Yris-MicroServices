package com.testyris.testyris.service;

import com.testyris.testyris.model.CategoryEntity;
import com.testyris.testyris.model.LocationEntity;
import com.testyris.testyris.repository.CategoryRepository;
import com.testyris.testyris.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Long saveCategory(CategoryEntity categoryEntity) {
        categoryRepository.save(categoryEntity);
        return categoryEntity.getId();
    }

    public Integer deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return id.intValue();
    }

    public Boolean updateCategory(CategoryEntity categoryEntity) {
        Boolean result = false;
        CategoryEntity categoryToUpdate = categoryRepository.findById(categoryEntity.getId()).orElse(null);
        if(categoryToUpdate != null){
            categoryToUpdate.setName(categoryEntity.getName());
            categoryToUpdate.setType(categoryEntity.getType());
            categoryToUpdate.setFillColor(categoryEntity.getFillColor());
            categoryToUpdate.setStrokeColor(categoryEntity.getStrokeColor());
            categoryToUpdate.setzIndex(categoryEntity.getzIndex());
            result = true;
        }
        categoryRepository.save(categoryToUpdate);
        return result;
    }

    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }

}
