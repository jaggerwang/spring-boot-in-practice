package net.jaggerwang.sbip.entity;

import java.time.LocalDateTime;

import lombok.*;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileBO {
    public enum Region {
        // 本地存储
        LOCAL
    }

    public enum ThumbType {
        // 小
        SMALL,
        // 中
        MIDDLE,
        // 大
        LARGE,
        // 超大
        HUGE
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private String name;

        private Long size;

        private String type;
    }

    private Long id;

    private Long userId;

    private Region region;

    private String bucket;

    private String path;

    private Meta meta;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
