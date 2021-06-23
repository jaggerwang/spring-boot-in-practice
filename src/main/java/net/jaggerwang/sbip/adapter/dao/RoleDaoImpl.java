package net.jaggerwang.sbip.adapter.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.mapper.RoleMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.Role;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.RoleBO;
import net.jaggerwang.sbip.usecase.port.dao.RoleDao;

/**
 * @author Jagger Wang
 */
@Component
public class RoleDaoImpl extends AbstractDao implements RoleDao {
    private final RoleMapper roleMapper;

    public RoleDaoImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleBO save(RoleBO roleBO) {
        var role = roleMapper.selectById(roleBO.getId());
        if (role == null) {
            role  = Role.fromBO(roleBO);
            roleMapper.insert(role);
        } else {
            roleMapper.updateById(role);
        }
        return role.toBO();
    }

    @Override
    public Optional<RoleBO> findById(Long id) {
        return Optional.ofNullable(roleMapper.selectById(id)).map(Role::toBO);
    }

    @Override
    public Optional<RoleBO> findByName(String name) {
        var queryMapper = new QueryWrapper<Role>();
        queryMapper.eq("name", name);
        return Optional.ofNullable(roleMapper.selectOne(queryMapper)).map(Role::toBO);
    }

    @Override
    public List<RoleBO> rolesOfUser(Long userId) {
        return roleMapper.selectRolesOfUser(userId)
                .stream().map(Role::toBO).collect(Collectors.toList());
    }

    @Override
    public List<RoleBO> rolesOfUser(String username) {
        return roleMapper.selectRolesOfUserByUsername(username)
                .stream().map(Role::toBO).collect(Collectors.toList());
    }
}
