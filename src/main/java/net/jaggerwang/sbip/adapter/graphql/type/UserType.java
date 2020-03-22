package net.jaggerwang.sbip.adapter.graphql.type;

import graphql.schema.DataFetcher;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserType implements Type {
    @Autowired
    UserAvatarDataFetcher avatarDataFetcher;
    @Autowired
    UserStatDataFetcher statDataFetcher;
    @Autowired
    UserFollowingDataFetcher followingDataFetcher;

    @Override
    public Map<String, DataFetcher> dataFetchers() {
        var m = new HashMap<String, DataFetcher>();
        m.put("avatar", avatarDataFetcher);
        m.put("stat", statDataFetcher);
        m.put("following", followingDataFetcher);
        return m;
    }
}
