package net.jaggerwang.sbip.usecase.port.repository;

import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.PostEntity;

public interface PostRepository {
    PostEntity save(PostEntity postEntity);

    void delete(Long id);

    Optional<PostEntity> findById(Long id);

    List<PostEntity> published(Long userId, Long limit, Long offset);

    Long publishedCount(Long userId);

    void like(Long userId, Long postId);

    void unlike(Long userId, Long postId);

    List<PostEntity> liked(Long userId, Long limit, Long offset);

    Long likedCount(Long userId);

    List<PostEntity> following(Long userId, Long limit, Long beforeId, Long afterId);

    Long followingCount(Long userId);

    Boolean isLiked(Long userId, Long postId);
}
