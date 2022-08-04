package com.maynim.dao;

import com.maynim.util.HibernateTestUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class UserDaoTest {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

}
