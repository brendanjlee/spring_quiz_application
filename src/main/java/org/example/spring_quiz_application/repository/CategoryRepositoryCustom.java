package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findAllCategories();
}
