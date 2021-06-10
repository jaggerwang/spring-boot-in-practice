package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jagger Wang
 */
@Mapper
public interface UserMapper {
    /**
     * 插入用户
     * @param user
     */
    void insert(User user);

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    /**
     * 删除用户
     * @param id
     */
    void delete(Long id);

    /**
     * 查询用户
     * @param id
     * @return
     */
    User select(Long id);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User selectByUsername(String username);

    /**
     * 根据手机查询用户
     * @param mobile
     * @return
     */
    User selectByMobile(String mobile);

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    User selectByEmail(String email);

    /**
     * 查询某个用户的关注用户
     * @param followerId
     * @param limit
     * @param offset
     * @return
     */
    List<User> selectFollowing(Long followerId, Long limit, Long offset);

    /**
     * 查询某个用户的关注用户数
     * @param followerId
     * @return
     */
    Long selectFollowingCount(Long followerId);

    /**
     * 查询某个用户的粉丝
     * @param followingId
     * @param limit
     * @param offset
     * @return
     */
    List<User> selectFollower(Long followingId, Long limit, Long offset);

    /**
     * 查询某个用户的粉丝数
     * @param followingId
     * @return
     */
    Long selectFollowerCount(Long followingId);
}
