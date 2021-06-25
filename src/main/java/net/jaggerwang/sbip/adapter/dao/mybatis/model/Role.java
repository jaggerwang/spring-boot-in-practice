package net.jaggerwang.sbip.adapter.dao.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.RoleBO;

import java.time.LocalDateTime;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Role fromBO(RoleBO roleBO) {
        return Role.builder()
                .id(roleBO.getId())
                .name(roleBO.getName())
                .createdAt(roleBO.getCreatedAt())
                .updatedAt(roleBO.getUpdatedAt())
                .build();
    }

    public RoleBO toBO() {
        return RoleBO.builder()
                .id(id)
                .name(name)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}