package net.jaggerwang.sbip.adapter.dao.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.UserBO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Jagger Wang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private Long avatarId;

    private String intro;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static User fromBO(UserBO userBO) {
        var intro = userBO.getIntro();
        if (intro == null) {
            intro = "";
        }

        return User.builder()
                .id(userBO.getId())
                .username(userBO.getUsername())
                .password(userBO.getPassword())
                .mobile(userBO.getMobile())
                .email(userBO.getEmail())
                .avatarId(userBO.getAvatarId())
                .intro(intro)
                .createdAt(userBO.getCreatedAt())
                .updatedAt(userBO.getUpdatedAt())
                .build();
    }

    public UserBO toBO() {
        return UserBO.builder()
                .id(id)
                .username(username)
                .password(password)
                .mobile(mobile)
                .email(email)
                .avatarId(avatarId)
                .intro(intro)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
