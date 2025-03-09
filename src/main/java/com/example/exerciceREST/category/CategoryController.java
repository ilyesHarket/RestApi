package com.example.exerciceREST.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")

public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    public Category CreateCategory(@RequestBody Category category){
        return this.categoryService.newCategory(category);
    }

    @GetMapping("/")
    public List<Category> getAllCategories(){
        return this.categoryService.getCategories();
    }

    //get category by id
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Integer id){
        return this.categoryService.getCategoryById(id);
    }
}
