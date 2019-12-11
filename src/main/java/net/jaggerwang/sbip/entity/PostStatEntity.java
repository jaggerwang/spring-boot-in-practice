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
public class PostStatEntity {
    private Long id;

    private Long postId;

    private long likeCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
