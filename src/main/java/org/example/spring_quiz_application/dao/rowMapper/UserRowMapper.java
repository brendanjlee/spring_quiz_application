package org.example.spring_quiz_application.dao.rowMapper;

import org.example.spring_quiz_application.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setAdmin(rs.getBoolean("is_admin"));
        user.setActive(rs.getBoolean("is_active"));
        return user;
    }
}
