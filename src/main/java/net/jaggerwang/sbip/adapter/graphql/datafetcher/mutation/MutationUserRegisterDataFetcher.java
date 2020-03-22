package net.jaggerwang.sbip.adapter.graphql.datafetcher.mutation;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.api.security.annotation.PermitAll;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MutationUserRegisterDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    @PermitAll
    public Object get(DataFetchingEnvironment env) {
        var userInput = objectMapper.convertValue(env.getArgument("user"), UserEntity.class);
        var userEntity = userUsecase.register(userInput);

        loginUser(userInput.getUsername(), userInput.getPassword());

        return userEntity;
    }
}
