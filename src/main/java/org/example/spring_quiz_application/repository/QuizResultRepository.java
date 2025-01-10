package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult,
        Integer>, QuizResultRepositoryCustom {
    List<QuizResult> findQuizResultsByUserId(int userId);
}
