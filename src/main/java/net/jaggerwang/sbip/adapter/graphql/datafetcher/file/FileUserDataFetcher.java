package net.jaggerwang.sbip.adapter.graphql.datafetcher.file;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import net.jaggerwang.sbip.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileUserDataFetcher extends AbstractFileDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment env) {
        FileEntity fileEntity = env.getSource();
        return userUsecase.info(fileEntity.getUserId()).get();
    }
}
