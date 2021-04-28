package net.jaggerwang.sbip.adapter.api.controller.dto;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.FileBO;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private Long id;

    private Long userId;

    private FileBO.Region region;

    private String bucket;

    private String path;

    private FileBO.Meta meta;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String url;

    private Map<FileBO.ThumbType, String> thumbs;

    public static FileDTO fromBO(FileBO fileBO) {
        return FileDTO.builder().id(fileBO.getId()).userId(fileBO.getUserId()).region(fileBO.getRegion())
                .bucket(fileBO.getBucket()).path(fileBO.getPath()).meta(fileBO.getMeta())
                .createdAt(fileBO.getCreatedAt()).updatedAt(fileBO.getUpdatedAt()).build();
    }

    public FileBO toBO() {
        return FileBO.builder().id(id).userId(userId).region(region).bucket(bucket).path(path).meta(meta)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
