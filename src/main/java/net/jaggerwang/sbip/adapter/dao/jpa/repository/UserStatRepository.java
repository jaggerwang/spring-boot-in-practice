package net.jaggerwang.sbip.adapter.dao.jpa.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.UserStat;

/**
 * @author Jagger Wang
 */
@Repository
public interface UserStatRepository extends JpaRepository<UserStat, Long>, QuerydslPredicateExecutor<UserStat> {
    Optional<UserStat> findByUserId(Long userId);
}
