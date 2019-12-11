package net.jaggerwang.sbip.adapter.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.RoleRepo;
import net.jaggerwang.sbip.adapter.repository.jpa.UserRoleRepo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.RoleDO;
import net.jaggerwang.sbip.entity.RoleEntity;
import net.jaggerwang.sbip.usecase.port.repository.RoleRepository;

@Component
public class RoleRepositoryImpl implements RoleRepository {
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public RoleEntity save(RoleEntity roleEntity) {
        return roleRepo.save(RoleDO.fromEntity(roleEntity)).toEntity();
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleRepo.findById(id).map(roleDO -> roleDO.toEntity());
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleRepo.findByName(name).map(roleDO -> roleDO.toEntity());
    }

    @Override
    public List<RoleEntity> rolesOfUser(Long userId) {
        return userRoleRepo.rolesOfUser(userId);
    }

    @Override
    public List<RoleEntity> rolesOfUser(String username) {
        return userRoleRepo.rolesOfUser(username);
    }
}
