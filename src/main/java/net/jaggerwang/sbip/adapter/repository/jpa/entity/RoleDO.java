package net.jaggerwang.sbip.adapter.repository.jpa.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.RoleEntity;

@Entity(name = "Role")
@Table(name = "role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static RoleDO fromEntity(RoleEntity roleEntity) {
        return RoleDO.builder().id(roleEntity.getId()).name(roleEntity.getName())
                .createdAt(roleEntity.getCreatedAt()).updatedAt(roleEntity.getUpdatedAt()).build();
    }

    public RoleEntity toEntity() {
        return RoleEntity.builder().id(id).name(name).createdAt(createdAt).updatedAt(updatedAt)
                .build();
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null)
            createdAt = LocalDateTime.now();
    }
}
