package net.jaggerwang.sbip.adapter.graphql;

import java.util.Optional;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.UserEntity;

@Component
public class UserDataFetcher extends AbstractDataFetcher {
    public DataFetcher avatar() {
        return env -> {
            UserEntity userEntity = env.getSource();
            if (userEntity.getAvatarId() == null) {
                return Optional.empty();
            }
            return fileUsecase.info(userEntity.getAvatarId());
        };
    }

    public DataFetcher stat() {
        return env -> {
            UserEntity userEntity = env.getSource();
            return statUsecase.userStatInfoByUserId(userEntity.getId());
        };
    }

    public DataFetcher following() {
        return env -> {
            UserEntity userEntity = env.getSource();
            return userUsecase.isFollowing(loggedUserId(), userEntity.getId());
        };
    }
}
