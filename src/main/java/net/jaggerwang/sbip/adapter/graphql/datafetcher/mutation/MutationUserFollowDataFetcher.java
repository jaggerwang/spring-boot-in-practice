package net.jaggerwang.sbip.adapter.graphql.datafetcher.mutation;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.stereotype.Component;

@Component
public class MutationUserFollowDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var userId = Long.valueOf((Integer) env.getArgument("userId"));
        userUsecase.follow(loggedUserId(), userId);
        return true;
    }
}
