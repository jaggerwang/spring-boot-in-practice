package net.jaggerwang.sbip.usecase;

import java.time.LocalDateTime;
import net.jaggerwang.sbip.entity.PostStatBO;
import net.jaggerwang.sbip.entity.UserStatBO;
import net.jaggerwang.sbip.usecase.port.dao.PostStatDAO;
import net.jaggerwang.sbip.usecase.port.dao.UserStatDAO;
import org.springframework.stereotype.Component;

/**
 * @author Jagger Wang
 */
@Component
public class StatUsecase {
    private final UserStatDAO userStatDAO;
    private final PostStatDAO postStatDAO;

    public StatUsecase(UserStatDAO userStatDAO,
                       PostStatDAO postStatDAO) {
        this.userStatDAO = userStatDAO;
        this.postStatDAO = postStatDAO;
    }

    public UserStatBO userStatInfoByUserId(Long userId) {
        var userStatEntity = userStatDAO.findByUserId(userId);
        if (userStatEntity.isEmpty()) {
            return UserStatBO.builder().id(0L).userId(userId).createdAt(LocalDateTime.now())
                    .build();
        }

        return userStatEntity.get();
    }

    public PostStatBO postStatInfoByPostId(Long postId) {
        var postStatEntity = postStatDAO.findByPostId(postId);
        if (postStatEntity.isEmpty()) {
            return PostStatBO.builder().id(0L).postId(postId).createdAt(LocalDateTime.now())
                    .build();
        }

        return postStatEntity.get();
    }
}
