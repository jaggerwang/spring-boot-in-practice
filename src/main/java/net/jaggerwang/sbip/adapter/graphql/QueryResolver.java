package net.jaggerwang.sbip.adapter.graphql;

import java.util.List;
import java.util.Optional;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.api.security.annotation.PermitAll;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.UserEntity;

@Component
public class QueryResolver extends AbstractResolver implements GraphQLQueryResolver {
    @PermitAll
    public Optional<UserEntity> userLogged() {
        if (loggedUserId() == null) {
            return null;
        }

        return userUsecases.info(loggedUserId());
    }

    public UserEntity userLogout() {
        var userEntity = userUsecases.info(loggedUserId());

        logoutUser();

        return userEntity.get();
    }

    public UserEntity userInfo(Long id) {
        var userEntity = userUsecases.info(id);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("用户未找到");
        }

        return userEntity.get();
    }

    public List<UserEntity> userFollowing(Long userId, Long limit, Long offset) {
        if (limit == null) {
            limit = 20L;
        }

        return userUsecases.following(userId, limit, offset);
    }

    public Long userFollowingCount(Long userId) {
        return userUsecases.followingCount(userId);
    }

    public List<UserEntity> userFollower(Long userId, Long limit, Long offset) {
        if (limit == null) {
            limit = 20L;
        }

        return userUsecases.follower(userId, limit, offset);
    }

    public Long userFollowerCount(Long userId) {
        return userUsecases.followerCount(userId);
    }

    public PostEntity postInfo(Long id) {
        var postEntity = postUsecases.info(id);
        if (postEntity.isEmpty()) {
            throw new NotFoundException("动态未找到");
        }

        return postEntity.get();
    }

    public List<PostEntity> postPublished(Long userId, Long limit, Long offset) {
        if (limit == null) {
            limit = 10L;
        }

        return postUsecases.published(userId, limit, offset);
    }

    public Long postPublishedCount(Long userId) {
        return postUsecases.publishedCount(userId);
    }

    public List<PostEntity> postLiked(Long userId, Long limit, Long offset) {
        if (limit == null) {
            limit = 10L;
        }

        return postUsecases.liked(userId, limit, offset);
    }

    public Long postLikedCount(Long userId) {
        return postUsecases.likedCount(userId);
    }

    public List<PostEntity> postFollowing(Long limit, Long beforeId, Long afterId) {
        if (limit == null) {
            limit = 10L;
        }

        return postUsecases.following(loggedUserId(), limit, beforeId, afterId);
    }

    public Long postFollowingCount() {
        return postUsecases.followingCount(loggedUserId());
    }

    public FileEntity fileInfo(Long id) {
        var fileEntity = fileUsecases.info(id);
        if (fileEntity.isEmpty()) {
            throw new NotFoundException("文件未找到");
        }

        return fileEntity.get();
    }
}
