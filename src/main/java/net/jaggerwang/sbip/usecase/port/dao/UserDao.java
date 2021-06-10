package net.jaggerwang.sbip.usecase.port.dao;

import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.UserBO;

/**
 * @author Jagger Wang
 */
public interface UserDao {
    /**
     * 保存用户
     * @param userBO 要保存的用户
     * @return 已保存的用户
     */
    UserBO save(UserBO userBO);

    /**
     * 查找指定 ID 的用户
     * @param id 用户 ID
     * @return 用户
     */
    Optional<UserBO> findById(Long id);

    /**
     * 查找指定用户名的用户
     * @param username 用户名
     * @return 用户
     */
    Optional<UserBO> findByUsername(String username);

    /**
     * 查找指定手机号的用户
     * @param mobile 用户名
     * @return 用户
     */
    Optional<UserBO> findByMobile(String mobile);

    /**
     * 查找指定邮箱的用户
     * @param email 用户名
     * @return 用户
     */
    Optional<UserBO> findByEmail(String email);

    /**
     * 关注用户
     * @param followerId 用户 ID
     * @param followingId 要关注用户 ID
     */
    void follow(Long followerId, Long followingId);

    /**
     * 取消关注用户
     * @param followerId 用户 ID
     * @param followingId 已关注用户 ID
     */
    void unfollow(Long followerId, Long followingId);

    /**
     * 查找某个用户关注的用户
     * @param followerId 用户 ID
     * @param limit 返回数量
     * @param offset 起始位置
     * @return 用户列表
     */
    List<UserBO> following(Long followerId, Long limit, Long offset);

    /**
     * 统计某个用户关注的用户数量
     * @param followerId 用户 ID
     * @return 用户数量
     */
    Long followingCount(Long followerId);

    /**
     * 查找某个用户的关注者
     * @param followingId 用户 ID
     * @param limit 返回数量
     * @param offset 起始位置
     * @return 用户列表
     */
    List<UserBO> follower(Long followingId, Long limit, Long offset);

    /**
     * 统计某个用户的关注者数量
     * @param followingId 用户 ID
     * @return 用户数量
     */
    Long followerCount(Long followingId);

    /**
     * 是否关注了某个用户
     * @param followerId 用户 ID
     * @param followingId 被关注用户 ID
     * @return 是否关注
     */
    Boolean isFollowing(Long followerId, Long followingId);
}
