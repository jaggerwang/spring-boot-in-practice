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
import net.jaggerwang.sbip.entity.UserEntity;

@Entity(name = "User")
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDo {
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

    public static UserDo fromEntity(UserEntity userEntity) {
        return UserDo.builder().id(userEntity.getId()).username(userEntity.getUsername())
                .password(userEntity.getPassword()).mobile(userEntity.getMobile()).email(userEntity.getEmail())
                .avatarId(userEntity.getAvatarId()).intro(userEntity.getIntro()).createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt()).build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder().id(id).username(username).password(password).mobile(mobile).email(email)
                .avatarId(avatarId).intro(intro).createdAt(createdAt).updatedAt(updatedAt).build();
    }

    @PrePersist
    public void prePersist() {
        if (intro == null)
            intro = "";
        if (createdAt == null)
            createdAt = LocalDateTime.now();
    }
}
