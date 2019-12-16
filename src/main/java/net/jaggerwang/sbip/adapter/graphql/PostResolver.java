package net.jaggerwang.sbip.adapter.graphql;

import java.util.List;
import java.util.Optional;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.FileEntity;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.entity.PostStatEntity;
import net.jaggerwang.sbip.entity.UserEntity;

@Component
public class PostResolver extends BaseResolver implements GraphQLResolver<PostEntity> {
    public UserEntity user(PostEntity postEntity) {
        return userUsecases.info(postEntity.getUserId());
    }

    public List<FileEntity> images(PostEntity postEntity) {
        return fileUsecases.infos(postEntity.getImageIds(), false);
    }

    public Optional<FileEntity> video(PostEntity postEntity) {
        if (postEntity.getVideoId() == null) {
            return Optional.empty();
        }

        var fileEntity = fileUsecases.info(postEntity.getVideoId());
        return Optional.of(fileEntity);
    }

    public PostStatEntity stat(PostEntity postEntity) {
        return statUsecases.postStatInfoByPostId(postEntity.getId());
    }

    public Boolean liked(PostEntity postEntity) {
        return postUsecases.isLiked(loggedUserId(), postEntity.getId());
    }
}
