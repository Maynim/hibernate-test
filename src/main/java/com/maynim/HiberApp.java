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
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Payment payment = session.find(Payment.class, 1L);
                payment.setAmount(payment.getAmount() + 10);

                session.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                AuditReader auditReader = AuditReaderFactory.get(session2);
                Payment oldPayment = auditReader.find(Payment.class, 1L, new Date(1660063589903L));
                System.out.println();

                session2.getTransaction().commit();
            }
        }
    }
}

