package org.example.spring_quiz_application.dao.rowMapper;

import org.example.spring_quiz_application.domain.Question;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QuestionRowMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setId(rs.getInt("id"));
        question.setCategoryId(rs.getInt("category_id"));
        question.setText(rs.getString("text"));
        question.setActive(rs.getBoolean("is_active"));
        return question;
    }
}
