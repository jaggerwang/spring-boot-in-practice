package net.jaggerwang.sbip.adapter.graphql.datafetcher.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.api.security.annotation.PermitAll;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueryAuthLoggedDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    @PermitAll
    public Object get(DataFetchingEnvironment env) {
        return loggedUserId() != null ? userUsecase.info(loggedUserId()) : Optional.empty();
    }
}
