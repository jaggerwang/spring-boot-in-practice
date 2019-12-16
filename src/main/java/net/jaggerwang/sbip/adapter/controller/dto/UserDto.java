package net.jaggerwang.sbip.adapter.controller.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private Long avatarId;

    private String intro;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private FileDto avatar;

    private UserStatDto stat;

    private Boolean following;

    public static UserDto fromEntity(UserEntity userEntity) {
        return UserDto.builder().id(userEntity.getId()).username(userEntity.getUsername())
                .mobile(userEntity.getMobile()).email(userEntity.getEmail())
                .avatarId(userEntity.getAvatarId()).intro(userEntity.getIntro())
                .createdAt(userEntity.getCreatedAt()).updatedAt(userEntity.getUpdatedAt()).build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder().id(id).username(username).password(password).mobile(mobile)
                .email(email).avatarId(avatarId).intro(intro).createdAt(createdAt)
                .updatedAt(updatedAt).build();
    }
}
