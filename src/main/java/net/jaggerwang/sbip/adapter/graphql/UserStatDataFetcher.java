package net.jaggerwang.sbip.adapter.graphql;

import graphql.schema.DataFetcher;
import net.jaggerwang.sbip.entity.UserStatEntity;
import org.springframework.stereotype.Component;

@Component
public class UserStatDataFetcher extends AbstractDataFetcher {
    public DataFetcher user() {
        return env -> {
            UserStatEntity userStatEntity = env.getSource();
            return userUsecase.info(userStatEntity.getUserId()).get();
        };
    }
}
