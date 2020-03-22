package net.jaggerwang.sbip.adapter.graphql.datafetcher.userstat;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.UserStatEntity;
import org.springframework.stereotype.Component;

@Component
public class UserStatUserDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        UserStatEntity userStatEntity = env.getSource();
        return userUsecase.info(userStatEntity.getUserId()).get();
    }
}
