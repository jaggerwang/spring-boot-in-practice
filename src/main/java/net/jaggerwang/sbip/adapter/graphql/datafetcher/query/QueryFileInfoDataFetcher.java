package net.jaggerwang.sbip.adapter.graphql.datafetcher.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.AbstractDataFetcher;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class QueryFileInfoDataFetcher extends AbstractDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        var id = Long.valueOf((Integer) env.getArgument("id"));
        var fileEntity = fileUsecase.info(id);
        if (fileEntity.isEmpty()) {
            throw new NotFoundException("文件未找到");
        }
        return fileEntity.get();
    }
}
