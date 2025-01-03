package org.example.spring_quiz_application.dao;

import org.example.spring_quiz_application.dao.rowMapper.ChoiceRowMapper;
import org.example.spring_quiz_application.domain.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChoiceDAO {
    JdbcTemplate jdbcTemplate;
    ChoiceRowMapper rowMapper;

    @Autowired
    public ChoiceDAO(JdbcTemplate jdbcTemplate, ChoiceRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    // create a new answer choice
    public void createChoice(int questionId, String text, boolean isAnswer) {
        String query = "INSERT INTO choice (question_id, text, is_answer) " +
                "VALUES" +
                " (?, ?, ?)";
        jdbcTemplate.update(query, questionId, text, isAnswer);
    }

    // get answer choice by id
    public Choice getChoice(int id) {
        String query = "SELECT * FROM choice WHERE question_id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    // update choice by id
    public void updateChoice(Choice choice) {
        String query = "update Choice set question_id = ?, text = ?, " +
                "is_answer = ? where id = ?";
        jdbcTemplate.update(query, choice.getQuestionId(), choice.getText(),
                choice.isAnswer(), choice.getId());
    }

    // delete choice by id
    public void deleteChoice(int id) {
        String query = "DELETE FROM choice WHERE question_id = ?";
        jdbcTemplate.update(query, id);
    }
}
