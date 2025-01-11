package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuizResultRepositoryCustomImpl implements QuizResultRepositoryCustom {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public QuizResultRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<QuizResult> findQuizResultsByUserId(int userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuizResult> cq = cb.createQuery(QuizResult.class);
        Root<QuizResult> root = cq.from(QuizResult.class);

        // fetch related
        root.fetch("category", JoinType.LEFT);

        // join with users
        Join<QuizResult, User> userJoin = root.join("user");

        // filter by userId
        cq.select(root).where(cb.equal(userJoin.get("id"), userId));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public QuizResult findQuizResultById(int quizResultId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<QuizResult> cq = cb.createQuery(QuizResult.class);
        Root<QuizResult> root = cq.from(QuizResult.class);
        cq.select(root).where(cb.equal(root.get("id"), quizResultId));
        try {
            QuizResult quizResult =
                    entityManager.createQuery(cq).getSingleResult();
            return quizResult;
        } catch (NoResultException e) {
            System.out.println("NoResultException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Choice> findChoicesByQuestionId(int questionId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Choice> cq = cb.createQuery(Choice.class);
        Root<Choice> root = cq.from(Choice.class);
        cq.select(root).where(cb.equal(root.get("question").get("id"),
                questionId));
        try {
            return entityManager.createQuery(cq).getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

}
