package net.jaggerwang.sbip.adapter.dao.jpa.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.PostStat;

/**
 * @author Jagger Wang
 */
@Repository
public interface PostStatRepository extends JpaRepository<PostStat, Long>, QuerydslPredicateExecutor<PostStat> {
    Optional<PostStat> findByPostId(Long postId);
}
