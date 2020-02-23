package net.jaggerwang.sbip.adapter.graphql;

import java.util.Optional;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.PostEntity;

@Component
public class PostDataFetcher extends AbstractDataFetcher {
    public DataFetcher user() {
        return env -> {
            PostEntity postEntity = env.getSource();
            return userUsecase.info(postEntity.getUserId()).get();
        };
    }

    public DataFetcher images() {
        return env -> {
            PostEntity postEntity = env.getSource();
            return fileUsecase.infos(postEntity.getImageIds(), false);
        };
    }

    public DataFetcher video() {
        return env -> {
            PostEntity postEntity = env.getSource();
            if (postEntity.getVideoId() == null) {
                return Optional.empty();
            }
            return fileUsecase.info(postEntity.getVideoId());
        };
    }

    public DataFetcher stat() {
        return env -> {
            PostEntity postEntity = env.getSource();
            return statUsecase.postStatInfoByPostId(postEntity.getId());
        };
    }

    public DataFetcher liked() {
        return env -> {
            PostEntity postEntity = env.getSource();
            return postUsecase.isLiked(loggedUserId(), postEntity.getId());
        };
    }
}
