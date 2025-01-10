package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Question;

import java.util.List;

public interface QuestionRepositoryCustom {
    List<Question> findQuestionsByCategoryId(int categoryId);
}
