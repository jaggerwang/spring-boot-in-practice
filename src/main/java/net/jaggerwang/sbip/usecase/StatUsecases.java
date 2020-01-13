package net.jaggerwang.sbip.usecase;

import java.time.LocalDateTime;
import net.jaggerwang.sbip.entity.PostStatEntity;
import net.jaggerwang.sbip.entity.UserStatEntity;
import net.jaggerwang.sbip.usecase.port.repository.PostStatRepository;
import net.jaggerwang.sbip.usecase.port.repository.UserStatRepository;

public class StatUsecases {
    private final UserStatRepository userStatRepository;
    private final PostStatRepository postStatRepository;

    public StatUsecases(UserStatRepository userStatRepository,
            PostStatRepository postStatRepository) {
        this.userStatRepository = userStatRepository;
        this.postStatRepository = postStatRepository;
    }

    public UserStatEntity userStatInfoByUserId(Long userId) {
        var userStatEntity = userStatRepository.findByUserId(userId);
        if (userStatEntity.isEmpty()) {
            return UserStatEntity.builder().id(0L).userId(userId).createdAt(LocalDateTime.now())
                    .build();
        }

        return userStatEntity.get();
    }

    public PostStatEntity postStatInfoByPostId(Long postId) {
        var postStatEntity = postStatRepository.findByPostId(postId);
        if (postStatEntity.isEmpty()) {
            return PostStatEntity.builder().id(0L).postId(postId).createdAt(LocalDateTime.now())
                    .build();
        }

        return postStatEntity.get();
    }
}
