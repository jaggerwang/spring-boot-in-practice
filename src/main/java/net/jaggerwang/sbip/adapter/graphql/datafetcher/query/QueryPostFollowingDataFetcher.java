package net.jaggerwang.sbip.adapter.graphql.datafetcher.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import org.springframework.stereotype.Component;

@Component
public class QueryPostFollowingDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var limit = env.getArgument("limit") != null ?
                Long.valueOf((Integer) env.getArgument("limit")) : 10L;
        var beforeId = env.getArgument("beforeId") != null ?
                Long.valueOf((Integer) env.getArgument("beforeId")) : null;
        var afterId = env.getArgument("afterId") != null ?
                Long.valueOf((Integer) env.getArgument("afterId")) : null;
        return postUsecase.following(loggedUserId(), limit, beforeId, afterId);
    }
}
