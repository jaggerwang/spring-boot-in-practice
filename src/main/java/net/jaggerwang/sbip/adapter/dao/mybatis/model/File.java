package net.jaggerwang.sbip.adapter.dao.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.FileBO;

import java.time.LocalDateTime;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private Long id;

    private Long userId;

    private FileBO.Region region;

    private String bucket;

    private String path;

    private FileBO.Meta meta;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static File fromBO(FileBO fileBO) {
        return File.builder().id(fileBO.getId()).userId(fileBO.getUserId()).region(fileBO.getRegion())
                .bucket(fileBO.getBucket()).path(fileBO.getPath()).meta(fileBO.getMeta())
                .createdAt(fileBO.getCreatedAt()).updatedAt(fileBO.getUpdatedAt()).build();
    }

    public FileBO toBO() {
        return FileBO.builder().id(id).userId(userId).region(region).bucket(bucket).path(path).meta(meta)
                .createdAt(createdAt).updatedAt(updatedAt).build();
    }
}
