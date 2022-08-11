package com.maynim.dao;

import com.maynim.entity.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;


public class CompanyRepository extends RepositoryBase<Integer, Company> {

    public CompanyRepository(EntityManager entityManager) {
        super(Company.class, entityManager);
    }


}
