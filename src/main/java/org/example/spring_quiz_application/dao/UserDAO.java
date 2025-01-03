package org.example.spring_quiz_application.dao;

import org.example.spring_quiz_application.dao.rowMapper.UserRowMapper;
import org.example.spring_quiz_application.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {
    JdbcTemplate jdbcTemplate;
    UserRowMapper rowMapper;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate, UserRowMapper rowMapper,
                   NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // get all users
    public List<User> getAllUsers() {
        String query = "select * from user";
        return jdbcTemplate.query(query, rowMapper);
    }

    // create a new user
    public void createUser(String email, String firstName, String lastName,
                           String passwordHash, boolean isAdmin,
                           boolean isActive) {
        String query = "insert into User (email, first_name, last_name, " +
                "password_hash, is_admin, is_active)"
                + "values (?,?,?,?,?,?)";
        jdbcTemplate.update(query, email, firstName, lastName, passwordHash,
                isAdmin, isActive);
    }

    public void createUser(String email, String firstName, String lastName,
                           String passwordHash) {
        createUser(email, firstName, lastName, passwordHash, false, true);
    }

    // get user by id
    public User getUserById(int id) {
        String query = "select * from user where id = ?";
        try {
            return jdbcTemplate.queryForObject(query, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // update user by id
    public void updateUser(User user) {
        String query = "update user set email = ?, first_name = ?," +
                "password_hash = ?, is_admin = ?," +
                "is_active = ? where id = ?";
        jdbcTemplate.update(query,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPasswordHash(),
                user.isAdmin(),
                user.isActive(),
                user.getId());
    }

    // delete user by id
    public void deleteUser(int id) {
        String query = "delete from user where id = ?";
        jdbcTemplate.update(query, id);
    }

    // activate or suspend user
    public void activateUser(int id) {
        String query = "update user set is_active = true where id = ?";
        jdbcTemplate.update(query, id);
    }

    public void deactivateUser(int id) {
        String query = "update user set is_active = false where id = ?";
        jdbcTemplate.update(query, id);
    }

    // promote or demote user to admin
    public void promoteUser(int id) {
        String query = "update user set is_admin = true where id = ?";
        jdbcTemplate.update(query, id);
    }

    public void demoteUser(int id) {
        String query = "update user set is_admin = false where id = ?";
        jdbcTemplate.update(query, id);
    }

    // log in a user
}
