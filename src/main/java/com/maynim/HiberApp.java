package com.maynim;


import com.maynim.entity.Payment;
import com.maynim.entity.User;
import com.maynim.entity.UserChat;
import com.maynim.util.HibernateUtil;
import com.maynim.util.TestDataImporter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAccumulator;

@Slf4j
public class HiberApp {

    @Transactional
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            TestDataImporter.importData(sessionFactory);
            User user = null;
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                user = session.find(User.class, 1L);
                user.getCompany().getName();
                User user1 = session.find(User.class, 1L);

                List<Payment> payment = session.createQuery("SELECT p FROM Payment p WHERE p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
//                        .setCacheRegion("queries")
                        .getResultList();

                session.getTransaction().commit();
            }

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                User user2 = session.find(User.class, 1L);
                user2.getCompany().getName();
                List<Payment> payment = session.createQuery("SELECT p FROM Payment p WHERE p.receiver.id = :userId", Payment.class)
                        .setParameter("userId", 1L)
                        .setCacheable(true)
//                        .setCacheRegion("queries")
                        .getResultList();
                session.getTransaction().commit();
            }
        }
    }
}

