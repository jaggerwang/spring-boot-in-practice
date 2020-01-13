package net.jaggerwang.sbip.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    public enum Type {
        TEXT, IMAGE, VIDEO
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
