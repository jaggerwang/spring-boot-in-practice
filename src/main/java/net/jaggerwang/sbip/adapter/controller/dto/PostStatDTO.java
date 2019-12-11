package net.jaggerwang.sbip.adapter.controller.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.PostStatEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostStatDTO {
    private Long id;

    private Long postId;

    private long likeCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private PostDTO post;

    public static PostStatDTO fromEntity(PostStatEntity postStatEntity) {
        return PostStatDTO.builder().id(postStatEntity.getId()).postId(postStatEntity.getPostId())
                .likeCount(postStatEntity.getLikeCount()).createdAt(postStatEntity.getCreatedAt())
                .updatedAt(postStatEntity.getUpdatedAt()).build();
    }

    public PostStatEntity toEntity() {
        return PostStatEntity.builder().id(id).postId(postId).likeCount(likeCount)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
