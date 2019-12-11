package net.jaggerwang.sbip.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostLikeDO;

@Repository
public interface PostLikeRepo extends JpaRepository<PostLikeDO, Long> {
        @Transactional
        void deleteByUserIdAndPostId(Long userId, Long postId);

        @Query(value = "SELECT IF(COUNT(*)>0,'true','false') FROM post_like pl "
                        + "WHERE pl.user_id = :user_id AND pl.post_id = :post_id",
                        nativeQuery = true)
        boolean isLiked(@Param("user_id") Long userId, @Param("post_id") Long postId);
}
