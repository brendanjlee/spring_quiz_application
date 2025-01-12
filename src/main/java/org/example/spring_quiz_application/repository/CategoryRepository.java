package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>,
        CategoryRepositoryCustom {
    Category findCategoryById(int id);

    List<Category> findAllCategories();
}
