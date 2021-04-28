package net.jaggerwang.sbip.adapter.dao.jpa.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.adapter.dao.jpa.converter.FileMetaConverter;
import net.jaggerwang.sbip.entity.FileBO;

/**
 * @author Jagger Wang
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private FileBO.Region region;

    private String bucket;

    private String path;

    @Convert(converter = FileMetaConverter.class)
    private FileBO.Meta meta;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
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

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
