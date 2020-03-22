package net.jaggerwang.sbip.adapter.graphql.datafetcher.mutation;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.stereotype.Component;

@Component
public class MutationUserSendMobileVerifyCodeDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        String type = env.getArgument("type");
        String mobile = env.getArgument("mobile");
        return userUsecase.sendMobileVerifyCode(type, mobile);
    }
}
