package net.jaggerwang.sbip.adapter.graphql.type;

import graphql.schema.DataFetcher;
import net.jaggerwang.sbip.adapter.graphql.datafetcher.mutation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MutationType implements Type {
    @Autowired
    MutationAuthLoginDataFetcher authLoginDataFetcher;
    @Autowired
    MutationUserRegisterDataFetcher userRegisterDataFetcher;
    @Autowired
    MutationUserModifyDataFetcher userModifyDataFetcher;
    @Autowired
    MutationUserSendMobileVerifyCodeDataFetcher userSendMobileVerifyCodeDataFetcher;
    @Autowired
    MutationUserFollowDataFetcher userFollowDataFetcher;
    @Autowired
    MutationUserUnfollowDataFetcher userUnfollowDataFetcher;
    @Autowired
    MutationPostPublishDataFetcher postPublishDataFetcher;
    @Autowired
    MutationPostDeleteDataFetcher postDeleteDataFetcher;
    @Autowired
    MutationPostLikeDataFetcher postLikeDataFetcher;
    @Autowired
    MutationPostUnlikeDataFetcher postUnlikeDataFetcher;

    @Override
    public Map<String, DataFetcher> dataFetchers() {
        var m = new HashMap<String, DataFetcher>();
        m.put("authLogin", authLoginDataFetcher);
        m.put("userRegister", userRegisterDataFetcher);
        m.put("userModify", userModifyDataFetcher);
        m.put("userSendMobileVerifyCode", userSendMobileVerifyCodeDataFetcher);
        m.put("userFollow", userFollowDataFetcher);
        m.put("userUnfollow", userUnfollowDataFetcher);
        m.put("postPublish", postPublishDataFetcher);
        m.put("postDelete", postDeleteDataFetcher);
        m.put("postLike", postLikeDataFetcher);
        m.put("postUnlike", postUnlikeDataFetcher);
        return m;
    }
}
