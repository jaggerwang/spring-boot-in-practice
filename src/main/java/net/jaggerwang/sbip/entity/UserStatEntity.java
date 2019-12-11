package net.jaggerwang.sbip.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatEntity {
    private Long id;

    private Long userId;

    private long postCount;

    private long likeCount;

    private long followingCount;

    private long followerCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
