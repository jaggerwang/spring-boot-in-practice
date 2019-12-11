package net.jaggerwang.sbip.usecase.port.repository;

import java.util.Optional;

import net.jaggerwang.sbip.entity.UserStatEntity;

public interface UserStatRepository {
    UserStatEntity save(UserStatEntity userStatEntity);

    Optional<UserStatEntity> findById(Long id);

    Optional<UserStatEntity> findByUserId(Long userId);
}
