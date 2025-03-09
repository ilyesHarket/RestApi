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



}
