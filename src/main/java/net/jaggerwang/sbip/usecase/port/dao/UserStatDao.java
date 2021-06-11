package net.jaggerwang.sbip.usecase.port.dao;

import java.util.Optional;

import net.jaggerwang.sbip.entity.UserStatBO;

/**
 * @author Jagger Wang
 */
public interface UserStatDao {
    /**
     * 保存用户统计
     * @param userStatBO 要保存的用户统计
     * @return 已保存的用户统计
     */
    UserStatBO save(UserStatBO userStatBO);

    /**
     * 查找指定 ID 的用户统计
     * @param id 用户统计 ID
     * @return 用户统计
     */
    Optional<UserStatBO> findById(Long id);

    /**
     * 查找指定用户的统计
     * @param userId 用户 ID
     * @return 用户统计
     */
    Optional<UserStatBO> findByUserId(Long userId);
}
