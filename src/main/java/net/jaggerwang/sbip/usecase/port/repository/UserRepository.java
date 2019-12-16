package net.jaggerwang.sbip.usecase.port.repository;

import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.UserEntity;

public interface UserRepository {
    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByMobile(String mobile);

    Optional<UserEntity> findByEmail(String email);

    void follow(Long followerId, Long followingId);

    void unfollow(Long followerId, Long followingId);

    List<UserEntity> following(Long followerId, Long limit, Long offset);

    Long followingCount(Long followerId);

    List<UserEntity> follower(Long followingId, Long limit, Long offset);

    Long followerCount(Long followingId);

    Boolean isFollowing(Long followerId, Long followingId);
}
