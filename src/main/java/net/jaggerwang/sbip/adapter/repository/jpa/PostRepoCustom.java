package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.List;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostDO;

public interface PostRepoCustom {
    List<PostDO> published(Long userId, Long limit, Long offset);

    Long publishedCount(Long userId);

    List<PostDO> liked(Long userId, Long limit, Long offset);

    Long likedCount(Long userId);

    List<PostDO> following(Long userId, Long limit, Long beforeId, Long afterId);

    Long followingCount(Long userId);
}
