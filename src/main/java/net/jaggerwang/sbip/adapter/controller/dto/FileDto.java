package net.jaggerwang.sbip.adapter.controller.dto;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.FileEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private Long id;

    private Long userId;

    private FileEntity.Region region;

    private String bucket;

    private String path;

    private FileEntity.Meta meta;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDto user;

    private String url;

    private Map<FileEntity.ThumbType, String> thumbs;

    public static FileDto fromEntity(FileEntity fileEntity) {
        return FileDto.builder().id(fileEntity.getId()).userId(fileEntity.getUserId()).region(fileEntity.getRegion())
                .bucket(fileEntity.getBucket()).path(fileEntity.getPath()).meta(fileEntity.getMeta())
                .createdAt(fileEntity.getCreatedAt()).updatedAt(fileEntity.getUpdatedAt()).build();
    }

    public FileEntity toEntity() {
        return FileEntity.builder().id(id).userId(userId).region(region).bucket(bucket).path(path).meta(meta)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
