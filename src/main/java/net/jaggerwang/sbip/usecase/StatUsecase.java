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
        var userStatBO = userStatDAO.findByUserId(userId);
        if (userStatBO.isEmpty()) {
            return UserStatBO.builder().id(0L).userId(userId).createdAt(LocalDateTime.now())
                    .build();
        }

        return userStatBO.get();
    }

    public PostStatBO postStatInfoByPostId(Long postId) {
        var postStatBO = postStatDAO.findByPostId(postId);
        if (postStatBO.isEmpty()) {
            return PostStatBO.builder().id(0L).postId(postId).createdAt(LocalDateTime.now())
                    .build();
        }

        return postStatBO.get();
    }
}
