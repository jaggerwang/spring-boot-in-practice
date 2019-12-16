package net.jaggerwang.sbip.adapter.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseRepositoryImpl {
    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected JPAQueryFactory jpaQueryFactory;
}
