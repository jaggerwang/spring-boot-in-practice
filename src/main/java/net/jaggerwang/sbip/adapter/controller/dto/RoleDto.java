package net.jaggerwang.sbip.adapter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.RoleEntity;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static RoleDto fromEntity(RoleEntity roleEntity) {
        return RoleDto.builder().id(roleEntity.getId()).name(roleEntity.getName())
                .createdAt(roleEntity.getCreatedAt()).updatedAt(roleEntity.getUpdatedAt()).build();
    }

    public RoleEntity toEntity() {
        return RoleEntity.builder().id(id).name(name).createdAt(createdAt).updatedAt(updatedAt)
                .build();
    }
}
