package org.example.spring_quiz_application.dao;

import org.example.spring_quiz_application.dao.rowMapper.QuizResultRowMapper;
import org.example.spring_quiz_application.domain.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class QuizResultDAO {
    JdbcTemplate jdbcTemplate;
    QuizResultRowMapper rowMapper;

    @Autowired
    public QuizResultDAO(JdbcTemplate jdbcTemplate,
                         QuizResultRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    // create a new quiz result
    public int createQuizResult(int userId, int categoryId,
                                LocalDateTime timeStart,
                                LocalDateTime timeEnd) {
        String query = "insert into quiz_result (user_id, category_id, " +
                "time_start, time_end) values (?,?,?,?)";
        // todo Fix something about this

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setInt(2, categoryId);
            ps.setTimestamp(3, Timestamp.valueOf(timeStart));
            ps.setTimestamp(4, Timestamp.valueOf(timeEnd));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    // get all quiz results
    public List<QuizResult> getAllQuizResults() {
        String query = "select * from quiz_result";
        return jdbcTemplate.query(query, rowMapper);
    }

    // get quiz result by ID
    public QuizResult getQuizResultById(int id) {
        String query = "select * from quiz_result where id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    // get quiz results for specific user
    public List<QuizResult> getQuizResultsByUserId(int userId) {
        String query = "select * from quiz_result where user_id = ?";
        return jdbcTemplate.query(query, rowMapper, userId);
    }

    // delete quiz result
    public void deleteQuizResult(int id) {
        String query = "delete from quiz_result where id = ?";
        jdbcTemplate.update(query, id);
    }
}
