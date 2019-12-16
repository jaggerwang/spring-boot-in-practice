package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserDo;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDo, Long>, QuerydslPredicateExecutor<UserDo> {
        Optional<UserDo> findByUsername(String username);

        Optional<UserDo> findByMobile(String mobile);

        Optional<UserDo> findByEmail(String email);
}
