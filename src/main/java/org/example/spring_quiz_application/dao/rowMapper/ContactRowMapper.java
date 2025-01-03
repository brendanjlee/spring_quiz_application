package org.example.spring_quiz_application.dao.rowMapper;

import org.example.spring_quiz_application.domain.Contact;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class ContactRowMapper implements RowMapper<Contact> {
    @Override
    public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("id"));
        contact.setSubject(rs.getString("subject"));
        contact.setEmail(rs.getString("email"));
        contact.setTimeSubmitted(rs.getObject("time_submitted",
                LocalDateTime.class));
        return contact;
    }
}
