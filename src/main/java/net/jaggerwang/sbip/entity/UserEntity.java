package net.jaggerwang.sbip.entity;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private Long avatarId;

    private String intro;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
