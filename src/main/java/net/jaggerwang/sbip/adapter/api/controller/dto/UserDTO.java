package net.jaggerwang.sbip.adapter.api.controller.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.UserBO;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private Long avatarId;

    private String intro;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private FileDTO avatar;

    private UserStatDTO stat;

    private Boolean following;

    public static UserDTO fromBO(UserBO userBO) {
        return UserDTO.builder().id(userBO.getId()).username(userBO.getUsername())
                .mobile(userBO.getMobile()).email(userBO.getEmail())
                .avatarId(userBO.getAvatarId()).intro(userBO.getIntro())
                .createdAt(userBO.getCreatedAt()).updatedAt(userBO.getUpdatedAt()).build();
    }

    public UserBO toBO() {
        return UserBO.builder().id(id).username(username).password(password).mobile(mobile)
                .email(email).avatarId(avatarId).intro(intro).createdAt(createdAt)
                .updatedAt(updatedAt).build();
    }
}
