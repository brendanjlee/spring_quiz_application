package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.QuizResult;
import org.example.spring_quiz_application.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.List;

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
}
