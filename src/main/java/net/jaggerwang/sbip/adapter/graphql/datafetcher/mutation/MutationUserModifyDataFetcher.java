package net.jaggerwang.sbip.adapter.graphql.datafetcher.mutation;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.stereotype.Component;

@Component
public class MutationUserModifyDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var userInput = objectMapper.convertValue(env.getArgument("user"), UserEntity.class);
        String code = env.getArgument("code");
        if ((userInput.getMobile() != null
                && !userUsecase.checkMobileVerifyCode("modify", userInput.getMobile(), code))
                || userInput.getEmail() != null && !userUsecase.checkEmailVerifyCode("modify",
                userInput.getEmail(), code)) {
            throw new UsecaseException("验证码错误");
        }

        return userUsecase.modify(loggedUserId(), userInput);
    }
}
