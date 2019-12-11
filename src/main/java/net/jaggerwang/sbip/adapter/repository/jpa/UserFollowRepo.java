package net.jaggerwang.sbip.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserFollowDO;

@Repository
public interface UserFollowRepo extends JpaRepository<UserFollowDO, Long> {
        @Transactional
        void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

        @Query(value = "SELECT IF(COUNT(*)>0,'true','false') FROM user_follow uf "
                        + "WHERE uf.follower_id = :follower_id AND uf.following_id = :following_id",
                        nativeQuery = true)
        boolean isFollowing(@Param("follower_id") Long followerId,
                        @Param("following_id") Long followingId);
}
