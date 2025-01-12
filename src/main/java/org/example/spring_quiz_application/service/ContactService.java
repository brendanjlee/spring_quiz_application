package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.model.Contact;
import org.example.spring_quiz_application.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

    @Transactional
    public void submitContact(String email, String subject, String message,
                              LocalDateTime submitTime) {
        Contact contact = Contact.builder()
                .email(email)
                .subject(subject)
                .message(message)
                .timeSubmitted(submitTime)
                .build();

        try {
            contactRepository.save(contact);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
