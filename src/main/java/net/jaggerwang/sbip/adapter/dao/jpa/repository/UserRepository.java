package net.jaggerwang.sbip.adapter.dao.jpa.repository;

import java.util.Optional;

import net.jaggerwang.sbip.adapter.dao.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Jagger Wang
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
        Optional<User> findByUsername(String username);

        Optional<User> findByMobile(String mobile);

        Optional<User> findByEmail(String email);
}
