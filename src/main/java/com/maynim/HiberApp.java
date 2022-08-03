package com.maynim;


import com.maynim.entity.Company;
import com.maynim.entity.PersonalInfo;
import com.maynim.entity.User;
import com.maynim.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Slf4j
public class HiberApp {

    public static void main(String[] args) {

        Company company = Company.builder()
                .name("KFC")
                .build();

//        User user = User.builder()
//                .username("Alex@m.ru")
//                .personalInfo(PersonalInfo.builder()
//                        .firstName("Alex")
//                        .lastName("Man")
//                        .build())
//                .company(company)
//                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                session1.beginTransaction();

//                session1.save(user);
//                session1.save(company);
//                company.addUser(user);

                session1.getTransaction().commit();
            }

        }
    }
}
