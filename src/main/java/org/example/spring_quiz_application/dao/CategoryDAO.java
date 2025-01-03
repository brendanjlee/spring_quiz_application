package org.example.spring_quiz_application.dao;

import org.example.spring_quiz_application.dao.rowMapper.CategoryRowMapper;
import org.example.spring_quiz_application.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAO {
    JdbcTemplate jdbcTemplate;
    CategoryRowMapper rowMapper;

    @Autowired
    public CategoryDAO(JdbcTemplate jdbcTemplate,
                       CategoryRowMapper categoryRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = categoryRowMapper;
    }

    // create a new category
    public void createCategory(String name) {
        String query = "insert into category (name) values (?)";
        jdbcTemplate.update(query, name);
    }

    // get all categories
    public List<Category> getAllCategories() {
        String query = "select * from category";
        return jdbcTemplate.query(query, rowMapper);
    }

    // get category by id
    public Category getCategoryById(int id) {
        String query = "select * from category where id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    // update category by id
    public void updateCategory(Category category) {
        String query = "update category set name = ? where id = ?";
        jdbcTemplate.update(query, category.getName(), category.getId());
    }

    // delete category by id
    public void deleteCategory(int id) {
        String query = "delete from category where id = ?";
        jdbcTemplate.update(query, id);
    }

    // get all questions for category by ID
    // todo double check output
    public List<Category> getQuestionsByCategory(int id) {
        String query = "select * from question where category_id = ?";
        return jdbcTemplate.query(query, rowMapper, id);
    }
}
