package org.example.spring_quiz_application.repository;

import org.example.spring_quiz_application.model.Question;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public QuestionRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Question> findQuestionsByCategoryId(int categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Question> cq = cb.createQuery(Question.class);
        Root<Question> root = cq.from(Question.class);

        cq.select(root).where(cb.equal(root.get("category").get("id"),
                categoryId));

        try {
            return entityManager.createQuery(cq).getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
