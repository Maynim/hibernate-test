package com.maynim.dao;

import com.maynim.entity.Payment;
import com.maynim.entity.User;
import org.hibernate.Session;

import java.util.List;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public List<User> findAll(Session session) {
        return session.createQuery("SELECT u FROM User u", User.class)
                .list();
    }

    public List<User> findAllByFirstName(Session session, String firstName) {
        return session.createQuery("SELECT u FROM User u WHERE u.personalInfo.firstName = :firstName",
                        User.class)
                .setParameter("firstName", firstName)
                .list();
    }

    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return session.createQuery("SELECT u FROM User u ORDER BY u.personalInfo.birthDate", User.class)
                .setMaxResults(limit)
                .list();
    }

    public List<User> findAllByCompanyName(Session session, String companyName) {
        return session.createQuery("select u FROM Company c JOIN c.users u WHERE u.company.name = :companyName", User.class)
                .setParameter("companyName", companyName)
                .list();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return session.createQuery("SELECT p FROM Payment p JOIN p.receiver u JOIN u.company c " +
                        "WHERE c.name = :companyName ORDER BY u.personalInfo.firstName, p.amount", Payment.class)
                .setParameter("companyName", companyName)
                .list();
    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return session.createQuery("SELECT AVG(p.amount) FROM Payment p JOIN p.receiver u " +
                        "WHERE u.personalInfo.firstName = :firstName " +
                        "AND u.personalInfo.lastName = :lastName", Double.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .uniqueResult();
    }

    public List<Object[]> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return session.createQuery("SELECT c.name, AVG(p.amount) FROM Company c " +
                        "JOIN c.users u " +
                        "JOIN u.payments p " +
                        "GROUP BY c.name " +
                        "ORDER BY c.name", Object[].class)
                .list();
    }

    public List<Object[]> isItPossible(Session session) {
        return session.createQuery("SELECT u, AVG(p.amount) FROM User u JOIN u.payments p " +
                        "GROUP BY u " +
                        "HAVING AVG(p.amount) > (SELECT AVG(p.amount) FROM Payment p) " +
                        "ORDER BY u.personalInfo.firstName", Object[].class)
                .list();
    }
}
