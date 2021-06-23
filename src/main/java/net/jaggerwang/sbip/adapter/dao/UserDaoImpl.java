package net.jaggerwang.sbip.adapter.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        var user = userMapper.selectById(userBO.getId());
        if (user == null) {
            user  = User.fromBO(userBO);
            userMapper.insert(user);
        } else {
            userMapper.updateById(user);
        }
        return user.toBO();
    }

    @Override
    public Optional<UserBO> findById(Long id) {
        return Optional.ofNullable(userMapper.selectById(id))
                .map(User::toBO);
    }

    @Override
    public Optional<UserBO> findByUsername(String username) {
        var queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        return Optional.ofNullable(userMapper.selectOne(queryWrapper))
                .map(User::toBO);
    }

    @Override
    public Optional<UserBO> findByEmail(String email) {
        var queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("email", email);
        return Optional.ofNullable(userMapper.selectOne(queryWrapper))
                .map(User::toBO);
    }

    @Override
    public Optional<UserBO> findByMobile(String mobile) {
        var queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("mobile", mobile);
        return Optional.ofNullable(userMapper.selectOne(queryWrapper))
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
        userFollowMapper.deleteByMap(Map.of(
                "follower_id", followerId,
                "following_id", followingId
        ));
    }

    @Override
    public List<UserBO> following(Long followerId, Long limit, Long offset) {
        return userMapper.selectFollowing(followerId, limit, offset)
                .stream().map(User::toBO).collect(Collectors.toList());
    }

    @Override
    public Long followingCount(Long followerId) {
        var queryWrapper = new QueryWrapper<UserFollow>();
        queryWrapper.eq("follower_id", followerId);
        return Long.valueOf(userFollowMapper.selectCount(queryWrapper));
    }

    @Override
    public List<UserBO> follower(Long followingId, Long limit, Long offset) {
        return userMapper.selectFollower(followingId, limit, offset)
                .stream().map(User::toBO).collect(Collectors.toList());
    }

    @Override
    public Long followerCount(Long followingId) {
        var queryWrapper = new QueryWrapper<UserFollow>();
        queryWrapper.eq("following_id", followingId);
        return Long.valueOf(userFollowMapper.selectCount(queryWrapper));
    }

    @Override
    public Boolean isFollowing(Long followerId, Long followingId) {
        var queryWrapper = new QueryWrapper<UserFollow>();
        queryWrapper.allEq(Map.of(
                "follower_id", followerId,
                "following_id", followingId
        ));
        return userFollowMapper.selectOne(queryWrapper) != null;
    }
}
