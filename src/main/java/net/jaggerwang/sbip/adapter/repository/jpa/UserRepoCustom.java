package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.List;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserDO;

public interface UserRepoCustom {
    List<UserDO> following(Long followerId, Long limit, Long offset);

    Long followingCount(Long followerId);

    List<UserDO> follower(Long followingId, Long limit, Long offset);

    Long followerCount(Long followingId);
}
