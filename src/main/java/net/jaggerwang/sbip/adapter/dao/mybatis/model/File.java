package net.jaggerwang.sbip.adapter.dao.mybatis.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
@TableName(autoResultMap = true)
public class File {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private FileBO.Region region;

    private String bucket;

    private String path;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private FileBO.Meta meta;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static File fromBO(FileBO fileBO) {
        return File.builder()
                .id(fileBO.getId())
                .userId(fileBO.getUserId())
                .region(fileBO.getRegion())
                .bucket(fileBO.getBucket())
                .path(fileBO.getPath())
                .meta(fileBO.getMeta())
                .createdAt(fileBO.getCreatedAt())
                .updatedAt(fileBO.getUpdatedAt())
                .build();
    }

    public FileBO toBO() {
        return FileBO.builder()
                .id(id)
                .userId(userId)
                .region(region)
                .bucket(bucket)
                .path(path)
                .meta(meta != null ? meta : FileBO.Meta.builder().type("").build())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
