package net.jaggerwang.sbip.adapter.graphql.datafetcher.post;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class PostUserDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        PostEntity postEntity = env.getSource();
        return userUsecase.info(postEntity.getUserId()).get();
    }
}
