package net.jaggerwang.sbip.adapter.dao.jpa.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jaggerwang.sbip.entity.UserBO;

/**
 * @author Jagger Wang
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String mobile;

    private String email;

    @Column(name = "avatar_id")
    private Long avatarId;

    private String intro;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static User fromBO(UserBO userBO) {
        return User.builder().id(userBO.getId()).username(userBO.getUsername())
                .password(userBO.getPassword()).mobile(userBO.getMobile()).email(userBO.getEmail())
                .avatarId(userBO.getAvatarId()).intro(userBO.getIntro()).createdAt(userBO.getCreatedAt())
                .updatedAt(userBO.getUpdatedAt()).build();
    }

    public UserBO toBO() {
        return UserBO.builder().id(id).username(username).password(password).mobile(mobile).email(email)
                .avatarId(avatarId).intro(intro).createdAt(createdAt).updatedAt(updatedAt).build();
    }

    @PrePersist
    public void prePersist() {
        if (intro == null) {
            intro = "";
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
