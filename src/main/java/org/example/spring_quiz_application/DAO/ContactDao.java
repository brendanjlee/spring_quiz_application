package org.example.spring_quiz_application.DAO;

import org.example.spring_quiz_application.config.HibernateConfigUtil;
import org.example.spring_quiz_application.model.Contact;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ContactDao {
    public Contact getContactById(Integer id) {

        Transaction transaction = null;
        Contact contact = null;
        try (Session session = HibernateConfigUtil.openSession()) {
            transaction = session.beginTransaction();

            contact = session.get(Contact.class, id);
            System.out.println(contact.getSubject());

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return contact;
    }
}
