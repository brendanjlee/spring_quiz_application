package org.example.spring_quiz_application.dao;

import org.example.spring_quiz_application.dao.rowMapper.ContactRowMapper;
import org.example.spring_quiz_application.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ContactDAO {
    JdbcTemplate jdbcTemplate;
    ContactRowMapper rowMapper;

    @Autowired
    public ContactDAO(JdbcTemplate jdbcTemplate,
                      ContactRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    // create a new contact
    public void createContact(String subject, String message, String email) {
        String query = "insert into Contact (subject, message, email, " +
                "time_submitted) values (?, ?, ?, ?)";
        jdbcTemplate.update(query, subject, message, email,
                LocalDateTime.now());
    }

    // get all contacts
    public List<Contact> getAllContacts() {
        String query = "select * from Contact";
        return jdbcTemplate.query(query, rowMapper);
    }

    // get contact by id
    public Contact getContactById(int id) {
        String query = "select * from Contact where id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    // delete contact by id
    public void deleteContact(int id) {
        String query = "delete from Contact where id = ?";
        jdbcTemplate.update(query, id);
    }
}
