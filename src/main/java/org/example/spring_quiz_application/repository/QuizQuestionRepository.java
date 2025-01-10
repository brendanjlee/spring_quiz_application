package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,
        Integer> {
}
