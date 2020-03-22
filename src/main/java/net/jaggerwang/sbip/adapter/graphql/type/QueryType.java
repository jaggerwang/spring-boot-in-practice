package net.jaggerwang.sbip.adapter.graphql.type;

import graphql.schema.DataFetcher;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueryType implements Type {
    @Autowired
    QueryAuthLogoutDataFetcher authLogoutDataFetcher;
    @Autowired
    QueryAuthLoggedDataFetcher authLoggedDataFetcher;
    @Autowired
    QueryUserInfoDataFetcher userInfoDataFetcher;
    @Autowired
    QueryUserFollowingDataFetcher userFollowingDataFetcher;
    @Autowired
    QueryUserFollowingCountDataFetcher userFollowingCountDataFetcher;
    @Autowired
    QueryUserFollowerDataFetcher userFollowerDataFetcher;
    @Autowired
    QueryUserFollowerCountDataFetcher userFollowerCountDataFetcher;
    @Autowired
    QueryPostInfoDataFetcher postInfoDataFetcher;
    @Autowired
    QueryPostPublishedDataFetcher postPublishedDataFetcher;
    @Autowired
    QueryPostPublishedCountDataFetcher postPublishedCountDataFetcher;
    @Autowired
    QueryPostLikedDataFetcher postLikedDataFetcher;
    @Autowired
    QueryPostLikedCountDataFetcher postLikedCountDataFetcher;
    @Autowired
    QueryPostFollowingDataFetcher postFollowingDataFetcher;
    @Autowired
    QueryPostFollowingCountDataFetcher postFollowingCountDataFetcher;
    @Autowired
    QueryFileInfoDataFetcher fileInfoDataFetcher;

    @Override
    public Map<String, DataFetcher> dataFetchers() {
        var m = new HashMap<String, DataFetcher>();
        m.put("authLogout", authLogoutDataFetcher);
        m.put("authLogged", authLoggedDataFetcher);
        m.put("userInfo", userInfoDataFetcher);
        m.put("userFollowing", userFollowingDataFetcher);
        m.put("userFollowingCount", userFollowingCountDataFetcher);
        m.put("userFollower", userFollowerDataFetcher);
        m.put("userFollowerCount", userFollowerCountDataFetcher);
        m.put("postInfo", postInfoDataFetcher);
        m.put("postPublished", postPublishedDataFetcher);
        m.put("postPublishedCount", postPublishedCountDataFetcher);
        m.put("postLiked", postLikedDataFetcher);
        m.put("postLikedCount", postLikedCountDataFetcher);
        m.put("postFollowing", postFollowingDataFetcher);
        m.put("postFollowingCount", postFollowingCountDataFetcher);
        m.put("fileInfo", fileInfoDataFetcher);
        return m;
    }
}
