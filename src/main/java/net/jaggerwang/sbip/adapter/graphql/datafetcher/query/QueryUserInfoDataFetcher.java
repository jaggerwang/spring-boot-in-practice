package net.jaggerwang.sbip.adapter.graphql.datafetcher.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class QueryUserInfoDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var id = Long.valueOf((Integer) env.getArgument("id"));
        var userEntity = userUsecase.info(id);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("用户未找到");
        }
        return userEntity.get();
    }
}
