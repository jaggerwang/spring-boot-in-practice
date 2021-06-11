package net.jaggerwang.sbip.adapter.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.RoleRepository;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.QRole;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.QUser;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.QUserRole;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.Role;
import net.jaggerwang.sbip.entity.RoleBO;
import net.jaggerwang.sbip.usecase.port.dao.RoleDao;

/**
 * @author Jagger Wang
 */
@Component
public class RoleDaoImpl implements RoleDao {
    private JPAQueryFactory jpaQueryFactory;
    private RoleRepository roleRepository;

    public RoleDaoImpl(JPAQueryFactory jpaQueryFactory, RoleRepository roleRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleBO save(RoleBO roleBO) {
        return roleRepository.save(Role.fromBO(roleBO)).toBO();
    }

    @Override
    public Optional<RoleBO> findById(Long id) {
        return roleRepository.findById(id).map(role -> role.toBO());
    }

    @Override
    public Optional<RoleBO> findByName(String name) {
        return roleRepository.findByName(name).map(role -> role.toBO());
    }

    @Override
    public List<RoleBO> rolesOfUser(Long userId) {
        var query = jpaQueryFactory.selectFrom(QRole.role)
                .join(QUserRole.userRole).on(QRole.role.id.eq(QUserRole.userRole.roleId))
                .where(QUserRole.userRole.userId.eq(userId));
        return query.fetch().stream().map(role -> role.toBO()).collect(Collectors.toList());
    }

    @Override
    public List<RoleBO> rolesOfUser(String username) {
        var query = jpaQueryFactory.selectFrom(QRole.role)
                .join(QUserRole.userRole).on(QRole.role.id.eq(QUserRole.userRole.roleId))
                .join(QUser.user).on(QUser.user.id.eq(QUserRole.userRole.userId))
                .where(QUser.user.username.eq(username));
        return query.fetch().stream().map(role -> role.toBO()).collect(Collectors.toList());
    }
}
