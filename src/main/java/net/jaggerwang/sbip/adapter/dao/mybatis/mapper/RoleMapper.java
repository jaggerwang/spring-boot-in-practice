package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jagger Wang
 */
@Mapper
public interface RoleMapper {
    /**
     * 插入角色
     * @param role
     */
    void insert(Role role);

    /**
     * 更新角色
     * @param role
     */
    void update(Role role);

    /**
     * 删除角色
     * @param id
     */
    void delete(Long id);

    /**
     * 查询角色
     * @param id
     * @return
     */
    Role select(Long id);

    /**
     * 根据名称查询角色
     * @param name
     * @return
     */
    Role selectByName(String name);

    /**
     * 查询某个用户拥有的角色
     * @param userId
     * @return
     */
    List<Role> selectRolesOfUser(Long userId);

    /**
     * 根据用户名查询某个用户拥有的角色
     * @param username
     * @return
     */
    List<Role> selectRolesOfUserByUsername(String username);
}
