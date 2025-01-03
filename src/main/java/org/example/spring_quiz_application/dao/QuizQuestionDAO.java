package org.example.spring_quiz_application.dao;

import org.example.spring_quiz_application.dao.rowMapper.QuizQuestionRowMapper;
import org.example.spring_quiz_application.domain.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizQuestionDAO {
    JdbcTemplate jdbcTemplate;
    QuizQuestionRowMapper rowMapper;

    @Autowired
    public QuizQuestionDAO(JdbcTemplate jdbcTemplate,
                           QuizQuestionRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    // create a new quiz question (user answers?)
    public void createQuizQuestion(int quizResult, int questionId,
                                   int userChoiceId) {
        String query = "insert into quiz_question (quiz_result_id, " +
                "question_id, user_choice_id) values (?, ?, ?)";
        jdbcTemplate.update(query, quizResult, questionId, userChoiceId);
    }

    // get quiz question by ID
    public QuizQuestion getQuizQuestion(int id) {
        String query = "select * from quiz_question where id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    // get all quiz questions for a quiz result id
    public List<QuizQuestion> getAllQuizQuestions(int quizResultId) {
        String query = "select * from quiz_question where quiz_result_id = ?";
        return jdbcTemplate.query(query, rowMapper, quizResultId);
    }

    // delete a quiz question by id
    public void deleteQuizQuestion(int id) {
        String query = "delete from quiz_question where id = ?";
        jdbcTemplate.update(query, id);
    }
}
