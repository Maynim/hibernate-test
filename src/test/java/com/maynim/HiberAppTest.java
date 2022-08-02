package com.maynim;

import com.maynim.entity.*;
import com.maynim.util.HibernateTestUtil;
import com.maynim.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class HiberAppTest {

    @Test
    void checkH2() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Company company = Company.builder()
                        .name("Google")
                        .build();

                session.save(company);

                session.getTransaction().commit();
            }
        }
    }

    @Test
    void localeInfo() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Company company = session.get(Company.class, 1);

                company.getUsers().forEach(System.out::println);

                session.getTransaction().commit();
            }
        }
    }

    @Test
    void checkManyToMany() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                User user = session.get(User.class, 3L);

                Chat chat = session.get(Chat.class, 1L);

                UserChat userChat = UserChat.builder()
                        .createdAt(Instant.now())
                        .createdBy(user.getUsername())
                        .build();

                userChat.setUser(user);
                userChat.setChat(chat);

                session.save(userChat);

                session.getTransaction().commit();
            }
        }
    }


    @Test
    void checkOneToOne() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                User user = session.get(User.class, 3L);
                System.out.println();
//
//                User user = User.builder()
//                        .username("Test4@bk.ru")
//                        .build();
//
//                Profile profile = Profile.builder()
//                        .language("ru")
//                        .street("Maya 13")
//                        .build();
//
//                profile.setUser(user);
//
//                session.save(user);
                session.getTransaction().commit();
            }
        }
    }
}
