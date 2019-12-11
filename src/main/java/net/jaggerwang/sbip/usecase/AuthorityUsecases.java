package net.jaggerwang.sbip.usecase;

import java.util.List;
import net.jaggerwang.sbip.entity.RoleEntity;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.port.repository.RoleRepository;

public class AuthorityUsecases extends BaseUsecases {
    private RoleRepository roleRepository;

    public AuthorityUsecases(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity createRole(RoleEntity roleEntity) {
        var role = RoleEntity.builder().name(roleEntity.getName()).build();
        return roleRepository.save(role);
    }

    public RoleEntity modifyRole(Long id, RoleEntity roleEntity) {
        var role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            throw new NotFoundException("角色未找到");
        }

        if (roleEntity.getName() != null) {
            role.setName(roleEntity.getName());
        }

        return roleRepository.save(role);
    }

    public RoleEntity roleInfo(Long id) {
        var roleEntity = roleRepository.findById(id);
        if (!roleEntity.isPresent()) {
            throw new NotFoundException("角色未找到");
        }

        return roleEntity.get();
    }

    public RoleEntity roleInfoByName(String name) {
        var roleEntity = roleRepository.findByName(name);
        if (!roleEntity.isPresent()) {
            throw new NotFoundException("角色未找到");
        }

        return roleEntity.get();
    }

    public List<RoleEntity> rolesOfUser(String username) {
        return roleRepository.rolesOfUser(username);
    }
}
