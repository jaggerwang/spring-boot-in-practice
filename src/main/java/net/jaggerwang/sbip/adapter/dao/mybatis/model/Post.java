package net.jaggerwang.sbip.adapter.dao.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.PostBO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;

    private Long userId;

    private PostBO.Type type;

    private String text;

    private List<Long> imageIds;

    private Long videoId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Post fromBO(PostBO postBO) {
        return Post.builder().id(postBO.getId()).userId(postBO.getUserId()).type(postBO.getType())
                .text(postBO.getText()).imageIds(postBO.getImageIds()).videoId(postBO.getVideoId())
                .createdAt(postBO.getCreatedAt()).updatedAt(postBO.getUpdatedAt()).build();
    }

    public PostBO toBO() {
        return PostBO.builder().id(id).userId(userId).type(type).text(text).imageIds(imageIds).videoId(videoId)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
