package org.example.spring_quiz_application.dao.rowMapper;

import org.example.spring_quiz_application.domain.Choice;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ChoiceRowMapper implements RowMapper<Choice> {
    @Override
    public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
        Choice choice = new Choice();
        choice.setId(rs.getInt("id"));
        choice.setQuestionId(rs.getInt("question_id"));
        choice.setText(rs.getString("text"));
        choice.setAnswer(rs.getBoolean("is_answer"));
        return choice;
    }
}
