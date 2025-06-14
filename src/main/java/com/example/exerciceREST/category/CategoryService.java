package com.example.exerciceREST.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    //constructeur
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //créer une nouvelle catégorie
    public Category newCategory(Category category){
        categoryRepository.save(category);
        return category;
    }

    //récupérer toutes les catégories
    public List<Category> getCategories(){
        return this.categoryRepository.findAll();
        //select * from category
    }

    //récupérer une catégorie par son id
    public Category getCategoryById(Integer id){
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category updateCategory(Integer id, Category updatedCategory) {
        Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
        
        existingCategory.setName(updatedCategory.getName());
        return categoryRepository.save(existingCategory);
    }

    //supprimer une catégorie
    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

}
