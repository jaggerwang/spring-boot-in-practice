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
public class UserStatDTO {
    private Long id;

    private Long userId;

    private long postCount;

    private long likeCount;

    private long followingCount;

    private long followerCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDTO user;

    public static UserStatDTO fromEntity(UserStatEntity userStatEntity) {
        return UserStatDTO.builder().id(userStatEntity.getId()).userId(userStatEntity.getUserId())
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
