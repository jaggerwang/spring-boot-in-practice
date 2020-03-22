package net.jaggerwang.sbip.adapter.graphql.datafetcher.mutation;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.PostEntity;
import org.springframework.stereotype.Component;

@Component
public class MutationPostPublishDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var postInput = objectMapper.convertValue(env.getArgument("post"), PostEntity.class);
        postInput.setUserId(loggedUserId());
        return postUsecase.publish(postInput);
    }
}
