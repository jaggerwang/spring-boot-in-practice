package net.jaggerwang.sbip.adapter.graphql.datafetcher.user;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAvatarDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        UserEntity userEntity = env.getSource();
        if (userEntity.getAvatarId() == null) {
            return Optional.empty();
        }
        return fileUsecase.info(userEntity.getAvatarId());
    }
}
