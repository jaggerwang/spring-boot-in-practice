package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jagger Wang
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
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
