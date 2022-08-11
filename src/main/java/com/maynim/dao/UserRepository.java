package com.maynim.dao;

import com.maynim.entity.Company;
import com.maynim.entity.User;

import javax.persistence.EntityManager;


public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }


}
