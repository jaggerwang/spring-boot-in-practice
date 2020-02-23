package net.jaggerwang.sbip.adapter.graphql;

import graphql.schema.DataFetcher;
import net.jaggerwang.sbip.entity.PostStatEntity;
import org.springframework.stereotype.Component;

@Component
public class PostStatDataFetcher extends AbstractDataFetcher {
    public DataFetcher post() {
        return env -> {
            PostStatEntity postStatEntity = env.getSource();
            return postUsecase.info(postStatEntity.getPostId()).get();
        };
    }
}
