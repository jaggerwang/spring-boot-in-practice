package net.jaggerwang.sbip.adapter.repository.jpa.entity;

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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.adapter.repository.jpa.converter.FileMetaConverter;
import net.jaggerwang.sbip.entity.FileEntity;

@Entity(name = "File")
@Table(name = "file")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private FileEntity.Region region;

    private String bucket;

    private String path;

    @Convert(converter = FileMetaConverter.class)
    private FileEntity.Meta meta;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static FileDO fromEntity(FileEntity fileEntity) {
        return FileDO.builder().id(fileEntity.getId()).userId(fileEntity.getUserId())
                .region(fileEntity.getRegion()).bucket(fileEntity.getBucket())
                .path(fileEntity.getPath()).meta(fileEntity.getMeta())
                .createdAt(fileEntity.getCreatedAt()).updatedAt(fileEntity.getUpdatedAt()).build();
    }

    public FileEntity toEntity() {
        return FileEntity.builder().id(id).userId(userId).region(region).bucket(bucket).path(path)
                .meta(meta).createdAt(createdAt).updatedAt(updatedAt).build();
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null)
            createdAt = LocalDateTime.now();
    }
}
