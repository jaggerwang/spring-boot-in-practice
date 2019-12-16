package net.jaggerwang.sbip.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserFollowDo;

@Repository
public interface UserFollowJpaRepository
                extends JpaRepository<UserFollowDo, Long>, QuerydslPredicateExecutor<UserFollowDo> {
        @Transactional
        void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

        Boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
