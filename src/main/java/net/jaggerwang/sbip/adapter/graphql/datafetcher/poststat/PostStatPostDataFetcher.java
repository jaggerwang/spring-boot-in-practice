package net.jaggerwang.sbip.adapter.graphql.datafetcher.poststat;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.PostStatEntity;
import org.springframework.stereotype.Component;

@Component
public class PostStatPostDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        PostStatEntity postStatEntity = env.getSource();
        return postUsecase.info(postStatEntity.getPostId()).get();
    }
}
