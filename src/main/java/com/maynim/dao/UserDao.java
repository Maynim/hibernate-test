package com.maynim.dao;

import com.maynim.dto.PaymentFilter;
import com.maynim.entity.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;

import java.util.List;

import static com.maynim.entity.QCompany.company;
import static com.maynim.entity.QPayment.payment;
import static com.maynim.entity.QUser.user;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public List<User> findAll(Session session) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .fetch();

    }

    public List<User> findAllByFirstName(Session session, String firstName) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.personalInfo.firstName.eq(firstName))
                .fetch();
    }

    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .orderBy(user.personalInfo.birthDate.asc())
                .limit(limit)
                .fetch();
    }

    public List<User> findAllByCompanyName(Session session, String companyName) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(company)
                .join(company.users, user)
                .where(company.name.eq(companyName))
                .fetch();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
        return new JPAQuery<Payment>(session)
                .select(payment)
                .from(payment)
                .join(payment.receiver, user).fetchJoin()
                .join(user.company, company)
                .where(company.name.eq(companyName))
                .orderBy(user.personalInfo.firstName.asc(), payment.amount.asc())
                .fetch();
    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, PaymentFilter filter) {
        Predicate predicate = QPredicate.builder()
                .add(filter.getFirstName(), user.personalInfo.firstName::eq)
                .add(filter.getLastName(), user.personalInfo.lastName::eq)
                .buildAnd();

        return new JPAQuery<Double>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.receiver, user)
                .where(predicate)
                .fetchOne();
    }

    public List<Tuple> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
        return  new JPAQuery<Tuple>(session)
                .select(company.name, payment.amount.avg())
                .from(company)
                .join(company.users, user)
                .join(user.payments, payment)
                .groupBy(company.name)
                .orderBy(company.name.asc())
                .fetch();
    }

    public List<Tuple> isItPossible(Session session) {
        return new JPAQuery<Tuple>(session)
                .select(user, payment.amount.avg())
                .from(user)
                .join(user.payments, payment)
                .groupBy(user.id)
                .having(payment.amount.avg().gt(
                        new JPAQuery<Double>(session)
                                .select(payment.amount.avg())
                                .from(payment)
                        )
                )
                .orderBy(user.personalInfo.firstName.asc())
                .fetch();
    }
}
