package net.jaggerwang.sbip.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.RoleJpaRepository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.QRoleDo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.QUserDo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.QUserRoleDo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.RoleDo;
import net.jaggerwang.sbip.entity.RoleEntity;
import net.jaggerwang.sbip.usecase.port.repository.RoleRepository;

@Component
public class RoleRepositoryImpl extends BaseRepositoryImpl implements RoleRepository {
    @Autowired
    private RoleJpaRepository roleJpaRepo;

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        return roleJpaRepo.save(RoleDo.fromEntity(roleEntity)).toEntity();
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleJpaRepo.findById(id).map(roleDo -> roleDo.toEntity());
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleJpaRepo.findByName(name).map(roleDo -> roleDo.toEntity());
    }

    @Override
    public List<RoleEntity> rolesOfUser(Long userId) {
        var role = QRoleDo.roleDo;
        var userRole = QUserRoleDo.userRoleDo;
        var query = jpaQueryFactory.selectFrom(role).join(userRole).on(role.id.eq(userRole.roleId))
                .where(userRole.userId.eq(userId));
        return query.fetch().stream().map(roleDo -> roleDo.toEntity()).collect(Collectors.toList());
    }

    @Override
    public List<RoleEntity> rolesOfUser(String username) {
        var role = QRoleDo.roleDo;
        var userRole = QUserRoleDo.userRoleDo;
        var user = QUserDo.userDo;
        var query = jpaQueryFactory.selectFrom(role).join(userRole).on(role.id.eq(userRole.roleId)).join(user)
                .on(user.id.eq(userRole.userId)).where(user.username.eq(username));
        return query.fetch().stream().map(roleDo -> roleDo.toEntity()).collect(Collectors.toList());
    }
}
