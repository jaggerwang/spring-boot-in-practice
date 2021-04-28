package net.jaggerwang.sbip.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;


/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostBO {
    public enum Type {
        // 文本
        TEXT,
        // 图形
        IMAGE,
        // 视频
        VIDEO
    }

    private Long id;

    private Long userId;

    private Type type;

    private String text;

    private List<Long> imageIds;

    private Long videoId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
