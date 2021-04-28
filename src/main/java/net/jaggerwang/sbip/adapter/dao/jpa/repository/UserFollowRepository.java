package net.jaggerwang.sbip.adapter.dao.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.UserFollow;

/**
 * @author Jagger Wang
 */
@Repository
public interface UserFollowRepository
                extends JpaRepository<UserFollow, Long>, QuerydslPredicateExecutor<UserFollow> {
        @Transactional
        void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

        Boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
