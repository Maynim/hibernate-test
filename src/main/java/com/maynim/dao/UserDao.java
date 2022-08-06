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
//        return session.createQuery("SELECT u FROM User u", User.class)
//                .list();

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .fetch();

    }

    public List<User> findAllByFirstName(Session session, String firstName) {
//        return session.createQuery("SELECT u FROM User u WHERE u.personalInfo.firstName = :firstName",
//                        User.class)
//                .setParameter("firstName", firstName)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<User> criteria = cb.createQuery(User.class);
//
//        Root<User> user = criteria.from(User.class);
//
//        criteria.select(user)
//                .where(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstName), firstName));
//
//        return session.createQuery(criteria).list();

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.personalInfo.firstName.eq(firstName))
                .fetch();
    }

    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
//        return session.createQuery("SELECT u FROM User u ORDER BY u.personalInfo.birthDate", User.class)
//                .setMaxResults(limit)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> criteria = cb.createQuery(User.class);
//
//        Root<User> user = criteria.from(User.class);
//
//        criteria.select(user)
//                .orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.birthDate)));
//
//        return session.createQuery(criteria)
//                .setMaxResults(limit)
//                .list();
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .orderBy(user.personalInfo.birthDate.asc())
                .limit(limit)
                .fetch();
    }

    public List<User> findAllByCompanyName(Session session, String companyName) {
//        return session.createQuery("select u FROM Company c JOIN c.users u WHERE u.company.name = :companyName", User.class)
//                .setParameter("companyName", companyName)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<User> criteria = cb.createQuery(User.class);
//        Root<Company> company = criteria.from(Company.class);
//        SetJoin<Company, User> users = company.join(Company_.users);
//
//        criteria.select(users)
//                .where(cb.equal(company.get(Company_.name), companyName));
//
//        return session.createQuery(criteria).list();

        return new JPAQuery<User>(session)
                .select(user)
                .from(company)
                .join(company.users, user)
                .where(company.name.eq(companyName))
                .fetch();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
//        return session.createQuery("SELECT p FROM Payment p JOIN p.receiver u JOIN u.company c " +
//                        "WHERE c.name = :companyName ORDER BY u.personalInfo.firstName, p.amount", Payment.class)
//                .setParameter("companyName", companyName)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Payment> criteria = cb.createQuery(Payment.class);
//
//        Root<Payment> payment = criteria.from(Payment.class);
//        Join<Payment, User> user = payment.join(Payment_.receiver);
//        Join<User, Company> company = user.join(User_.company);
//
//        criteria.select(payment)
//                .where(cb.equal(company.get(Company_.name), companyName))
//                .orderBy(
//                        cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstName)),
//                        cb.asc(payment.get(Payment_.amount))
//                );
//
//        return session.createQuery(criteria).list();


        return new JPAQuery<Payment>(session)
                .select(payment)
                .from(payment)
                .join(payment.receiver, user)
                .join(user.company, company)
                .where(company.name.eq(companyName))
                .orderBy(user.personalInfo.firstName.asc(), payment.amount.asc())
                .fetch();
    }

    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, PaymentFilter filter) {
//        return session.createQuery("SELECT AVG(p.amount) FROM Payment p JOIN p.receiver u " +
//                        "WHERE u.personalInfo.firstName = :firstName " +
//                              "AND u.personalInfo.lastName = :lastName", Double.class)
//                .setParameter("firstName", firstName)
//                .setParameter("lastName", lastName)
//                .uniqueResult();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Double> criteria = cb.createQuery(Double.class);
//
//        Root<Payment> payment = criteria.from(Payment.class);
//        Join<Payment, User> user = payment.join(Payment_.receiver);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        if(firstName != null){
//            predicates.add(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.firstName), firstName));
//        }
//        if(lastName != null){
//            predicates.add(cb.equal(user.get(User_.personalInfo).get(PersonalInfo_.lastName), lastName));
//        }
//
//        criteria.select(cb.avg(payment.get(Payment_.amount)))
//                .where(predicates.toArray(Predicate[]::new));
//
//        return session.createQuery(criteria).uniqueResult();
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
//        return session.createQuery("SELECT c.name, AVG(p.amount) FROM Company c " +
//                        "JOIN c.users u " +
//                        "JOIN u.payments p " +
//                        "GROUP BY c.name " +
//                        "ORDER BY c.name", Object[].class)
//                .list();
//
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<CompanyDto> criteria = cb.createQuery(CompanyDto.class);
//
//        Root<Company> company = criteria.from(Company.class);
//        SetJoin<Company, User> user = company.join(Company_.users);
//        ListJoin<User, Payment> payment = user.join(User_.payments);
//
////        criteria.multiselect(
////                company.get(Company_.name),
////                cb.avg(payment.get(Payment_.amount))
////        )
////                .groupBy(company.get(Company_.name))
////                .orderBy(cb.asc(company.get(Company_.name)));
//
//        criteria.select(
//                cb.construct(CompanyDto.class,
//                             company.get(Company_.name),
//                             cb.avg(payment.get(Payment_.amount)))
//                )
//                .groupBy(company.get(Company_.name))
//                .orderBy(cb.asc(company.get(Company_.name)));
//
//        return session.createQuery(criteria).list();
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
//        return session.createQuery("SELECT u, AVG(p.amount) FROM User u JOIN u.payments p " +
//                        "GROUP BY u " +
//                        "HAVING AVG(p.amount) > (SELECT AVG(p.amount) FROM Payment p) " +
//                        "ORDER BY u.personalInfo.firstName", Object[].class)
//                .list();

//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Tuple> criteria = cb.createQuery(Tuple.class);
//
//        Root<User> user = criteria.from(User.class);
//        ListJoin<User, Payment> payment = user.join(User_.payments);
//
//        Subquery<Double> subquery = criteria.subquery(Double.class);
//        Root<Payment> paymentSubquery = subquery.from(Payment.class);
//
//        criteria.select(cb.tuple(
//                        user,
//                        cb.avg(payment.get(Payment_.amount))
//                        )
//                )
//                .groupBy(user.get(User_.id))
//                .having(cb.gt(
//                        cb.avg(payment.get(Payment_.amount)),
//                        subquery.select(cb.avg(paymentSubquery.get(Payment_.amount)))
//                        )
//                )
//                .orderBy(cb.asc(user.get(User_.personalInfo).get(PersonalInfo_.firstName)));
//
//        return session.createQuery(criteria).list();
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
