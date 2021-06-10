package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jagger Wang
 */
@Mapper
public interface UserRoleMapper {
    /**
     * 插入用户拥有角色
     * @param userRole
     */
    void insert(UserRole userRole);

    /**
     * 更新用户拥有角色
     * @param userRole
     */
    void update(UserRole userRole);

    /**
     * 删除用户拥有角色
     * @param id
     */
    void delete(Long id);

    /**
     * 查询用户拥有角色
     * @param id
     * @return
     */
    UserRole select(Long id);
}
