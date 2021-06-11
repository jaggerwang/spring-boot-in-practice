package net.jaggerwang.sbip.adapter.dao.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.UserStatBO;

import java.time.LocalDateTime;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStat {
    private Long id;

    private Long userId;

    private Long postCount;

    private Long likeCount;

    private Long followingCount;

    private Long followerCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserStat fromBO(UserStatBO userStatBO) {
        return UserStat.builder()
                .id(userStatBO.getId())
                .userId(userStatBO.getUserId())
                .postCount(userStatBO.getPostCount())
                .likeCount(userStatBO.getLikeCount())
                .followingCount(userStatBO.getFollowingCount())
                .followerCount(userStatBO.getFollowerCount())
                .createdAt(userStatBO.getCreatedAt())
                .updatedAt(userStatBO.getUpdatedAt())
                .build();
    }

    public UserStatBO toBO() {
        return UserStatBO.builder().id(id).userId(userId).postCount(postCount)
                .likeCount(likeCount).followingCount(followingCount).followerCount(followerCount)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
