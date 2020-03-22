package net.jaggerwang.sbip.adapter.graphql.datafetcher.mutation;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.stereotype.Component;

@Component
public class MutationPostLikeDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var postId = Long.valueOf((Integer) env.getArgument("postId"));
        postUsecase.like(loggedUserId(), postId);
        return true;
    }
}
