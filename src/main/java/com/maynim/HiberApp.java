package com.maynim;


import com.maynim.entity.User;
import com.maynim.util.HibernateUtil;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HiberApp {
    public static void main(String[] args) {
        System.out.println("Start");

        User user = User.builder()
                .username("Max@m.ru")
                .firstName("Max")
                .lastName("Maxov")
                .build();


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user.setFirstName("Sveta");

                session2.refresh(user);

                session2.getTransaction().commit();
            }
        }
        System.out.println("End");
    }
}
