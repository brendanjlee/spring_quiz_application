package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.domain.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {
    void createCategory(String name);

    List<Category> getAllCategories();

    Category getCategoryById(int id);

    void updateCategory(Category category);

    void deleteCategory(int id);

    List<Category> getQuestionsByCategory(int id);
}
