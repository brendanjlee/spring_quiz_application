package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Choice;
import org.example.spring_quiz_application.model.Question;
import org.example.spring_quiz_application.model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult,
        Integer>, QuizResultRepositoryCustom {
    List<QuizResult> findAllQuizResults();

    List<QuizResult> findQuizResultsByUserId(int userId);

    QuizResult findQuizResultById(int quizResultId);
}
