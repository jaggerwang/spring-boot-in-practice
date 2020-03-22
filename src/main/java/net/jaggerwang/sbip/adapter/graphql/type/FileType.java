package net.jaggerwang.sbip.adapter.graphql.type;

import graphql.schema.DataFetcher;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.file.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FileType implements Type {
    @Autowired
    FileUserDataFetcher userDataFetcher;
    @Autowired
    FileUrlDataFetcher urlDataFetcher;
    @Autowired
    FileThumbsDataFetcher thumbsDataFetcher;

    @Override
    public Map<String, DataFetcher> dataFetchers() {
        var m = new HashMap<String, DataFetcher>();
        m.put("user", userDataFetcher);
        m.put("url", urlDataFetcher);
        m.put("thumbs", thumbsDataFetcher);
        return m;
    }
}
