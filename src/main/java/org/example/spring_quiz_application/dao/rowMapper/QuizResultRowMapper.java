package org.example.spring_quiz_application.dao.rowMapper;

import org.example.spring_quiz_application.domain.QuizResult;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class QuizResultRowMapper implements RowMapper<QuizResult> {
    @Override
    public QuizResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        QuizResult quizResult = new QuizResult();
        quizResult.setId(rs.getInt("id"));
        quizResult.setUserId(rs.getInt("user_id"));
        quizResult.setCategoryId(rs.getInt("category_id"));
        quizResult.setTimeStart(rs.getObject("time_start",
                LocalDateTime.class));
        quizResult.setTimeEnd(rs.getObject("time_end", LocalDateTime.class));
        return quizResult;
    }
}
