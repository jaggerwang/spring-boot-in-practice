package net.jaggerwang.sbip.adapter.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.jaggerwang.sbip.adapter.dao.mybatis.mapper.UserFollowMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.mapper.UserMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.User;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.UserFollow;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.UserBO;
import net.jaggerwang.sbip.usecase.port.dao.UserDao;

/**
 * @author Jagger Wang
 */
@Component
public class UserDaoImpl extends AbstractDao implements UserDao {
    private final UserMapper userMapper;
    private final UserFollowMapper userFollowMapper;

    public UserDaoImpl(UserMapper userMapper, UserFollowMapper userFollowMapper) {
        this.userMapper = userMapper;
        this.userFollowMapper = userFollowMapper;
    }

    @Override
    public UserBO save(UserBO userBO) {
        var user = userMapper.select(userBO.getId());
        if (user == null) {
            user  = User.fromBO(userBO);
            userMapper.insert(user);
        } else {
            userMapper.update(user);
        }
        return user.toBO();
    }

    @Override
    public Optional<UserBO> findById(Long id) {
        return Optional.ofNullable(userMapper.select(id))
                .map(User::toBO);
    }

    @Override
    public Optional<UserBO> findByUsername(String username) {
        return Optional.ofNullable(userMapper.selectByUsername(username))
                .map(User::toBO);
    }

    @Override
    public Optional<UserBO> findByEmail(String email) {
        return Optional.ofNullable(userMapper.selectByEmail(email))
                .map(User::toBO);
    }

    @Override
    public Optional<UserBO> findByMobile(String mobile) {
        return Optional.ofNullable(userMapper.selectByMobile(mobile))
                .map(User::toBO);
    }

    @Override
    public void follow(Long followerId, Long followingId) {
        var userFollow = UserFollow.builder()
                .followerId(followerId)
                .followingId(followingId)
                .build();
        userFollowMapper.insert(userFollow);
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {
        userFollowMapper.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Override
    public List<UserBO> following(Long followerId, Long limit, Long offset) {
        return userMapper.selectFollowing(followerId, limit, offset)
                .stream().map(User::toBO).collect(Collectors.toList());
    }

    @Override
    public Long followingCount(Long followerId) {
        return userMapper.selectFollowingCount(followerId);
    }

    @Override
    public List<UserBO> follower(Long followingId, Long limit, Long offset) {
        return userMapper.selectFollower(followingId, limit, offset)
                .stream().map(User::toBO).collect(Collectors.toList());
    }

    @Override
    public Long followerCount(Long followingId) {
        return userMapper.selectFollowerCount(followingId);
    }

    @Override
    public Boolean isFollowing(Long followerId, Long followingId) {
        return userFollowMapper.selectByFollowerIdAndFollowingId(followerId, followingId) != null;
    }
}
