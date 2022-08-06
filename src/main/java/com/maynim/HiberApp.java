package com.maynim;



import com.maynim.entity.User;
import com.maynim.util.HibernateUtil;
import com.maynim.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Slf4j
public class HiberApp {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                List<User> users = session.createQuery("SELECT u FROM User u", User.class).list();

                users.forEach(user -> System.out.println(user.getPayments().size()));
                users.forEach(user -> System.out.println(user.getCompany().getName()));

                session.getTransaction().commit();
            }
        }
    }
}
