package net.jaggerwang.sbip.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostLikeDo;

@Repository
public interface PostLikeJpaRepository
                extends JpaRepository<PostLikeDo, Long>, QuerydslPredicateExecutor<PostLikeDo> {
        @Transactional
        void deleteByUserIdAndPostId(Long userId, Long postId);

        Boolean existsByUserIdAndPostId(Long userId, Long postId);
}
