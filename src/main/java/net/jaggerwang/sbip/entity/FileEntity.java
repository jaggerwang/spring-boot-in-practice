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
public class FileEntity {
    public static enum Region {
        LOCAL
    }

    public static enum ThumbType {
        SMALL, MIDDLE, LARGE, HUGE
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
