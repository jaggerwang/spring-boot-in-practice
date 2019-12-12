package net.jaggerwang.sbip.adapter.graphql;

import java.util.List;
import java.util.Optional;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.api.security.annotation.PermitALL;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.UserEntity;

@Component
public class QueryResolver extends BaseResolver implements GraphQLQueryResolver {
    public UserEntity userLogout() {
        var userEntity = userUsecases.info(loggedUserId());

        logoutUser();

        return userEntity;
    }

    @PermitALL
    public Optional<UserEntity> userLogged() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken || !auth.isAuthenticated()) {
            return Optional.empty();
        }

        return Optional.of(userUsecases.info(loggedUserId()));
    }

    public UserEntity userInfo(Long id) {
        return userUsecases.info(id);
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
        return postUsecases.info(id);
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
        return fileUsecases.info(id);
    }
}
