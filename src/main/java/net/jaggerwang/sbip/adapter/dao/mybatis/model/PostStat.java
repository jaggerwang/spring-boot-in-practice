package net.jaggerwang.sbip.adapter.dao.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.PostStatBO;

import java.time.LocalDateTime;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostStat {
    private Long id;

    private Long postId;

    private Long likeCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static PostStat fromBO(PostStatBO postStatBO) {
        return PostStat.builder().id(postStatBO.getId()).postId(postStatBO.getPostId())
                .likeCount(postStatBO.getLikeCount()).createdAt(postStatBO.getCreatedAt())
                .updatedAt(postStatBO.getUpdatedAt()).build();
    }

    public PostStatBO toBO() {
        return PostStatBO.builder().id(id).postId(postId).likeCount(likeCount)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
