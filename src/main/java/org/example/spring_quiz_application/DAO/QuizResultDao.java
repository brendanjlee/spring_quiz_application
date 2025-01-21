package org.example.spring_quiz_application.DAO;

import org.example.spring_quiz_application.config.HibernateConfigUtil;
import org.example.spring_quiz_application.model.QuizResult;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class QuizResultDao {
    public QuizResult getQuizResultById(int id) {
        Transaction transaction = null;
        QuizResult quizResult = null;

        try (Session session = HibernateConfigUtil.openSession()) {
            transaction = session.beginTransaction();
            quizResult = (QuizResult) session.get(QuizResult.class, id);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return quizResult;
    }

    public List<QuizResult> getQuizResultsByUserId(int userId) {
        Transaction transaction = null;
        List<QuizResult> quizResults = null;

        try (Session session = HibernateConfigUtil.openSession()) {
            transaction = session.beginTransaction();

            String hql = "FROM QuizResult q WHERE q.user.id = :userId";
            quizResults = session.createQuery(hql).setParameter("userId",
                    userId).getResultList();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return quizResults;
    }
}
