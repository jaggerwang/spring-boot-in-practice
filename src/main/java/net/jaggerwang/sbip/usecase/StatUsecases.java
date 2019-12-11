package net.jaggerwang.sbip.usecase;

import java.time.LocalDateTime;
import net.jaggerwang.sbip.entity.PostStatEntity;
import net.jaggerwang.sbip.entity.UserStatEntity;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.port.repository.PostStatRepository;
import net.jaggerwang.sbip.usecase.port.repository.UserStatRepository;

public class StatUsecases extends BaseUsecases {
    private UserStatRepository userStatRepository;

    private PostStatRepository postStatRepository;

    public StatUsecases(UserStatRepository userStatRepository,
            PostStatRepository postStatRepository) {
        this.userStatRepository = userStatRepository;
        this.postStatRepository = postStatRepository;
    }

    public UserStatEntity userStatInfo(Long id) {
        var userStatEntity = userStatRepository.findById(id);
        if (!userStatEntity.isPresent()) {
            throw new NotFoundException("用户统计未找到");
        }

        return userStatEntity.get();
    }

    public UserStatEntity userStatInfoByUserId(Long userId) {
        var userStatEntity = userStatRepository.findByUserId(userId);
        if (!userStatEntity.isPresent()) {
            return UserStatEntity.builder().id(0L).userId(userId).createdAt(LocalDateTime.now())
                    .build();
        }

        return userStatEntity.get();
    }

    public PostStatEntity postStatInfo(Long id) {
        var postStatEntity = postStatRepository.findById(id);
        if (!postStatEntity.isPresent()) {
            throw new NotFoundException("动态统计未找到");
        }

        return postStatEntity.get();
    }

    public PostStatEntity postStatInfoByPostId(Long postId) {
        var postStatEntity = postStatRepository.findByPostId(postId);
        if (!postStatEntity.isPresent()) {
            return PostStatEntity.builder().id(0L).postId(postId).createdAt(LocalDateTime.now())
                    .build();
        }

        return postStatEntity.get();
    }
}
