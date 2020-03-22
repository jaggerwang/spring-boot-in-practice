package net.jaggerwang.sbip.adapter.graphql.type;

import graphql.schema.DataFetcher;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PostType implements Type {
    @Autowired
    PostUserDataFetcher userDataFetcher;
    @Autowired
    PostImagesDataFetcher imagesDataFetcher;
    @Autowired
    PostVideoDataFetcher videoDataFetcher;
    @Autowired
    PostStatDataFetcher statDataFetcher;
    @Autowired
    PostLikedDataFetcher likedDataFetcher;

    @Override
    public Map<String, DataFetcher> dataFetchers() {
        var m = new HashMap<String, DataFetcher>();
        m.put("user", userDataFetcher);
        m.put("images", imagesDataFetcher);
        m.put("video", videoDataFetcher);
        m.put("stat", statDataFetcher);
        m.put("liked", likedDataFetcher);
        return m;
    }
}
