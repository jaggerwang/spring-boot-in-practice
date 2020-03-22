package net.jaggerwang.sbip.adapter.graphql.type;

import graphql.schema.DataFetcher;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.userstat.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStatType implements Type {
    @Autowired
    UserStatUserDataFetcher userDataFetcher;

    @Override
    public Map<String, DataFetcher> dataFetchers() {
        var m = new HashMap<String, DataFetcher>();
        m.put("user", userDataFetcher);
        return m;
    }
}
