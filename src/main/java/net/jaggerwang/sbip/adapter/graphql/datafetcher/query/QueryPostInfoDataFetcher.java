package net.jaggerwang.sbip.adapter.graphql.datafetcher.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class QueryPostInfoDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var id = Long.valueOf((Integer) env.getArgument("id"));
        var postEntity = postUsecase.info(id);
        if (postEntity.isEmpty()) {
            throw new NotFoundException("动态未找到");
        }
        return postEntity.get();
    }
}
