package com.maynim.dao;

import com.maynim.entity.Payment;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;


public class PaymentRepository extends RepositoryBase<Long, Payment> {

    public PaymentRepository(EntityManager entityManager) {
        super(Payment.class, entityManager);
    }


}
