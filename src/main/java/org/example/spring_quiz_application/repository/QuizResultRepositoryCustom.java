package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Choice;
import org.example.spring_quiz_application.model.Question;
import org.example.spring_quiz_application.model.QuizResult;

import java.util.List;

public interface QuizResultRepositoryCustom {
    List<QuizResult> findQuizResultsByUserId(int userId);

    QuizResult findQuizResultById(int quizResultId);

    List<Choice> findChoicesByQuestionId(int questionId);
}
