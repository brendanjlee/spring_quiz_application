package org.example.spring_quiz_application.dao.rowMapper;

import org.example.spring_quiz_application.domain.QuizQuestion;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuizQuestionRowMapper implements RowMapper<QuizQuestion> {
    @Override
    public QuizQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setId(rs.getInt("id"));
        quizQuestion.setQuizResultId(rs.getInt("quiz_result_id"));
        quizQuestion.setQuestionId(rs.getInt("question_id"));
        quizQuestion.setUserChoiceId(rs.getInt("user_choice_id"));
        return quizQuestion;
    }
}
