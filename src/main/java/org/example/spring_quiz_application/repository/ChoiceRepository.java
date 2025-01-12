package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
}
