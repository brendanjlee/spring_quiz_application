package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>,
        QuestionRepositoryCustom {
    List<Question> findQuestionsByCategoryId(int categoryId);
}
