package net.jaggerwang.sbip.usecase.port.dao;

import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.RoleBO;

/**
 * @author Jagger Wang
 */
public interface RoleDao {
    /**
     * 保存角色
     * @param roleBO 要保存的角色
     * @return 已保存的角色
     */
    RoleBO save(RoleBO roleBO);

    /**
     * 查找指定 ID 的角色
     * @param id 角色 ID
     * @return 角色
     */
    Optional<RoleBO> findById(Long id);

    /**
     * 查找指定名字的角色
     * @param name 角色名
     * @return 角色
     */
    Optional<RoleBO> findByName(String name);

    /**
     * 查找指定用户（ID）的角色列表
     * @param userId 用户 ID
     * @return 角色列表
     */
    List<RoleBO> rolesOfUser(Long userId);

    /**
     * 查找指定用户（用户名）的角色列表
     * @param username 用户名
     * @return 角色列表
     */
    List<RoleBO> rolesOfUser(String username);
}
