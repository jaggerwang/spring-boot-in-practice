package net.jaggerwang.sbip.adapter.graphql.datafetcher.user;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserFollowingDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        UserEntity userEntity = env.getSource();
        return userUsecase.isFollowing(loggedUserId(), userEntity.getId());
    }
}
