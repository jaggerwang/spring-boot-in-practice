package net.jaggerwang.sbip.adapter.api.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Jagger Wang
 */
@Configuration(proxyBeanMethods = false)
@EntityScan("net.jaggerwang.sbip.adapter.dao.jpa.entity")
@EnableJpaRepositories("net.jaggerwang.sbip.adapter.dao.jpa.repository")
public class JpaConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
