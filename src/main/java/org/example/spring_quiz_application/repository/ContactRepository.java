package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
