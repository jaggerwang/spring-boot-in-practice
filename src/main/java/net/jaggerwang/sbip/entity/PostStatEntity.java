package net.jaggerwang.sbip.entity;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostStatEntity {
    private Long id;

    private Long postId;

    @Builder.Default
    private Long likeCount = 0L;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
