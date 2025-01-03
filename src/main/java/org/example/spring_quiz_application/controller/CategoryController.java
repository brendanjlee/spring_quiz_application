package org.example.spring_quiz_application.controller;

import org.example.spring_quiz_application.domain.Category;
import org.example.spring_quiz_application.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public void createCategory(@RequestBody Category category) {
        categoryService.createCategory(category.getName());
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable int id,
                               @RequestBody Category category) {
        category.setId(id);
        categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/{id}/questions")
    public List<Category> getQuestionsByCategory(@PathVariable int id) {
        // todo fix
        System.out.println("Got questions for category " + id);
        return categoryService.getQuestionsByCategory(id);
    }
}
