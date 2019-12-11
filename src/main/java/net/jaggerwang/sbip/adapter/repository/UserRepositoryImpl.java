package net.jaggerwang.sbip.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.UserRepo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserDO;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserFollowDO;
import net.jaggerwang.sbip.adapter.repository.jpa.UserFollowRepo;
import net.jaggerwang.sbip.entity.UserEntity;
import net.jaggerwang.sbip.usecase.port.repository.UserRepository;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserFollowRepo userFollowRepo;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepo.save(UserDO.fromEntity(userEntity)).toEntity();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepo.findById(id).map(userDO -> userDO.toEntity());
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepo.findByUsername(username).map(userDO -> userDO.toEntity());
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepo.findByEmail(email).map(userDO -> userDO.toEntity());
    }

    @Override
    public Optional<UserEntity> findByMobile(String mobile) {
        return userRepo.findByMobile(mobile).map(userDO -> userDO.toEntity());
    }

    @Override
    public void follow(Long followerId, Long followingId) {
        userFollowRepo.save(
                UserFollowDO.builder().followerId(followerId).followingId(followingId).build());
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {
        userFollowRepo.deleteByFollowerIdAndFollowingId(followerId, followerId);
    }

    @Override
    public List<UserEntity> following(Long followerId, Long limit, Long offset) {
        return userRepo.following(followerId, limit, offset).stream()
                .map(userDO -> userDO.toEntity()).collect(Collectors.toList());
    }

    @Override
    public Long followingCount(Long followerId) {
        return userRepo.followingCount(followerId);
    }

    @Override
    public List<UserEntity> follower(Long followingId, Long limit, Long offset) {
        return userRepo.follower(followingId, limit, offset).stream()
                .map(userDO -> userDO.toEntity()).collect(Collectors.toList());
    }

    @Override
    public Long followerCount(Long followingId) {
        return userRepo.followerCount(followingId);
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        return userFollowRepo.isFollowing(followerId, followingId);
    }
}
