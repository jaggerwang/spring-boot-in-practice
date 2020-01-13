package net.jaggerwang.sbip.adapter.controller.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.UserStatEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatDto {
    private Long id;

    private Long userId;

    private Long postCount;

    private Long likeCount;

    private Long followingCount;

    private Long followerCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserStatDto fromEntity(UserStatEntity userStatEntity) {
        return UserStatDto.builder().id(userStatEntity.getId()).userId(userStatEntity.getUserId())
                .postCount(userStatEntity.getPostCount()).likeCount(userStatEntity.getLikeCount())
                .followingCount(userStatEntity.getFollowingCount())
                .followerCount(userStatEntity.getFollowerCount())
                .createdAt(userStatEntity.getCreatedAt()).updatedAt(userStatEntity.getUpdatedAt())
                .build();
    }

    public UserStatEntity toEntity() {
        return UserStatEntity.builder().id(id).userId(userId).postCount(postCount)
                .likeCount(likeCount).followingCount(followingCount).followerCount(followerCount)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
