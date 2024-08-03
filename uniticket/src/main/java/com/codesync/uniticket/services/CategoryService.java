package com.codesync.uniticket.services;

import com.codesync.uniticket.entities.CategoryEntity;
import com.codesync.uniticket.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryEntity saveCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    public List<CategoryEntity> getCategories() { return categoryRepository.findAll();}

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryEntity updateCategory(CategoryEntity category) {
        CategoryEntity existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new EntityNotFoundException("Faculty with id " + category.getId() + " does not exist."));

        if (category.getFaculty() != null){
            existingCategory.setFaculty(category.getFaculty());
        }

        if (category.getDepartment() != null){
            existingCategory.setDepartment(category.getDepartment());
        }

        if (category.getTopic() != null){
            existingCategory.setTopic(category.getTopic());
        }

        return categoryRepository.save(existingCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteCategory(Long id) throws Exception {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
