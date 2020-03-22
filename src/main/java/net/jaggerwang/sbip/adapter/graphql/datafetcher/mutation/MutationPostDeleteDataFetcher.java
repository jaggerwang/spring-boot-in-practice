package net.jaggerwang.sbip.adapter.graphql.datafetcher.mutation;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.stereotype.Component;

@Component
public class MutationPostDeleteDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var id = Long.valueOf((Integer) env.getArgument("id"));
        var postEntity = postUsecase.info(id);
        if (postEntity.isEmpty()) {
            throw new NotFoundException("动态未找到");
        }
        if (!loggedUserId().equals(postEntity.get().getUserId())) {
            throw new UsecaseException("无权删除");
        }

        postUsecase.delete(id);
        return true;
    }
}
