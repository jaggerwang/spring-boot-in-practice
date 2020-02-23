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
public class PostResolver extends AbstractResolver implements GraphQLResolver<PostEntity> {
    public UserEntity user(PostEntity postEntity) {
        return userUsecase.info(postEntity.getUserId()).get();
    }

    public List<FileEntity> images(PostEntity postEntity) {
        return fileUsecase.infos(postEntity.getImageIds(), false);
    }

    public Optional<FileEntity> video(PostEntity postEntity) {
        if (postEntity.getVideoId() == null) {
            return Optional.empty();
        }

        return fileUsecase.info(postEntity.getVideoId());
    }

    public PostStatEntity stat(PostEntity postEntity) {
        return statUsecase.postStatInfoByPostId(postEntity.getId());
    }

    public Boolean liked(PostEntity postEntity) {
        return postUsecase.isLiked(loggedUserId(), postEntity.getId());
    }
}
