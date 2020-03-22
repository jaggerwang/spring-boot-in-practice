package net.jaggerwang.sbip.adapter.graphql.datafetcher.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.api.security.annotation.PermitAll;
import org.springframework.stereotype.Component;

@Component
public class QueryAuthLogoutDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    @PermitAll
    public Object get(DataFetchingEnvironment env) {
        var loggedUser = logoutUser();
        if (loggedUser.isEmpty()) {
            return null;
        }

        var userEntity = userUsecase.info(loggedUser.get().getId());
        return userEntity.get();
    }
}
