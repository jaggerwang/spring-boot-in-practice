package net.jaggerwang.sbip.adapter.graphql;

import java.util.Optional;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.api.security.annotation.PermitAll;

@Component
public class QueryDataFetcher extends AbstractDataFetcher {
    public DataFetcher userLogged() {
        return new DataFetcher() {
            @PermitAll
            @Override
            public Object get(DataFetchingEnvironment env) {
                return loggedUserId() != null ? userUsecase.info(loggedUserId()) : Optional.empty();
            }
        };
    }

    public DataFetcher userLogout() {
        return env -> {
            var loggedUser = logoutUser();
            if (loggedUser.isEmpty()) {
                return null;
            }

            var userEntity = userUsecase.info(loggedUser.get().getId());
            return userEntity.get();
        };
    }

    public DataFetcher userInfo() {
        return env -> {
            var id = Long.valueOf((Integer) env.getArgument("id"));
            var userEntity = userUsecase.info(id);
            if (userEntity.isEmpty()) {
                throw new NotFoundException("用户未找到");
            }
            return userEntity.get();
        };
    }

    public DataFetcher userFollowing() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : 20L;
            var offset = env.getArgument("offset") != null ?
                    Long.valueOf((Integer) env.getArgument("offset")) : null;
            return userUsecase.following(userId, limit, offset);
        };
    }

    public DataFetcher userFollowingCount() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            return userUsecase.followingCount(userId);
        };
    }

    public DataFetcher userFollower() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : 20L;
            var offset = env.getArgument("offset") != null ?
                    Long.valueOf((Integer) env.getArgument("offset")) : null;
            return userUsecase.follower(userId, limit, offset);
        };
    }

    public DataFetcher userFollowerCount() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            return userUsecase.followerCount(userId);
        };
    }

    public DataFetcher postInfo() {
        return env -> {
            var id = Long.valueOf((Integer) env.getArgument("id"));
            var postEntity = postUsecase.info(id);
            if (postEntity.isEmpty()) {
                throw new NotFoundException("动态未找到");
            }
            return postEntity.get();
        };
    }

    public DataFetcher postPublished() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : 10L;
            var offset = env.getArgument("offset") != null ?
                    Long.valueOf((Integer) env.getArgument("offset")) : null;
            return postUsecase.published(userId, limit, offset);
        };
    }

    public DataFetcher postPublishedCount() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            return postUsecase.publishedCount(userId);
        };
    }

    public DataFetcher postLiked() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : 10L;
            var offset = env.getArgument("offset") != null ?
                    Long.valueOf((Integer) env.getArgument("offset")) : null;
            return postUsecase.liked(userId, limit, offset);
        };
    }

    public DataFetcher postLikedCount() {
        return env -> {
            var userId = env.getArgument("userId") != null ?
                    Long.valueOf((Integer) env.getArgument("userId")) : null;
            return postUsecase.likedCount(userId);
        };
    }

    public DataFetcher postFollowing() {
        return env -> {
            var limit = env.getArgument("limit") != null ?
                    Long.valueOf((Integer) env.getArgument("limit")) : 10L;
            var beforeId = env.getArgument("beforeId") != null ?
                    Long.valueOf((Integer) env.getArgument("beforeId")) : null;
            var afterId = env.getArgument("afterId") != null ?
                    Long.valueOf((Integer) env.getArgument("afterId")) : null;
            return postUsecase.following(loggedUserId(), limit, beforeId, afterId);
        };
    }

    public DataFetcher postFollowingCount() {
        return env -> postUsecase.followingCount(loggedUserId());
    }

    public DataFetcher fileInfo() {
        return env -> {
            var id = Long.valueOf((Integer) env.getArgument("id"));
            var fileEntity = fileUsecase.info(id);
            if (fileEntity.isEmpty()) {
                throw new NotFoundException("文件未找到");
            }
            return fileEntity.get();
        };
    }
}
