package net.jaggerwang.sbip.usecase.port.repository;

import java.util.Optional;

import net.jaggerwang.sbip.entity.PostStatEntity;

public interface PostStatRepository {
    PostStatEntity save(PostStatEntity postStatEntity);

    Optional<PostStatEntity> findById(Long id);

    Optional<PostStatEntity> findByPostId(Long postId);
}
