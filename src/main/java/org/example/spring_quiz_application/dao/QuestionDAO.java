package org.example.spring_quiz_application.dao;

import org.example.spring_quiz_application.dao.rowMapper.QuestionRowMapper;
import org.example.spring_quiz_application.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class QuestionDAO {
    JdbcTemplate jdbcTemplate;
    QuestionRowMapper rowMapper;

    @Autowired
    public QuestionDAO(JdbcTemplate jdbcTemplate, QuestionRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    // create a new question
    public void createQuestion(int categoryId, String text, boolean isActive) {
        String query = "INSERT INTO question (category_id, text, is_active) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(query, categoryId, text, isActive);
    }

    // create new question with object
    public int addQuestion(Question question) {
        String query = "INSERT INTO question (category_id, text) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, question.getCategoryId());
            ps.setString(2, question.getText());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    // get all questions
    public List<Question> getAllQuestions() {
        String query = "SELECT * FROM question";
        return jdbcTemplate.query(query, rowMapper);
    }

    // get all questions by category
    public List<Question> getAllQuestionsByCategoryId(int categoryId) {
        String query = "SELECT * FROM question WHERE category_id = ?";
        return jdbcTemplate.query(query, rowMapper, categoryId);
    }

    // get question by ID
    public Question getQuestion(int questionId) {
        String query = "SELECT * FROM question WHERE id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, questionId);
    }

    // delete question by ID
    public void deleteQuestion(int questionId) {
        String query = "DELETE FROM question WHERE id = ?";
        jdbcTemplate.update(query, questionId);
    }

    // toggle question
    public void disableQuestion(int questionId) {
        String query = "UPDATE question SET is_active = false WHERE id = ?";
        jdbcTemplate.update(query, questionId);
    }

    public void enableQuestion(int questionId) {
        String query = "UPDATE question SET is_active = true WHERE id = ?";
        jdbcTemplate.update(query, questionId);
    }

    public void updateQuestion(int questionId, String text) {
        String query = "UPDATE question SET text = ? WHERE id " +
                "= ?";
        jdbcTemplate.update(query, text, questionId);
    }
}
