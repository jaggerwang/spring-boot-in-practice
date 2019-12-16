package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostStatDo;

@Repository
public interface PostStatJpaRepository extends JpaRepository<PostStatDo, Long>, QuerydslPredicateExecutor<PostStatDo> {
    Optional<PostStatDo> findByPostId(Long postId);
}
