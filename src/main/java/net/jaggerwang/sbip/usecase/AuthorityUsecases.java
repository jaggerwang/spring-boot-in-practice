package net.jaggerwang.sbip.usecase;

import java.util.List;

import net.jaggerwang.sbip.entity.RoleEntity;
import net.jaggerwang.sbip.usecase.port.repository.RoleRepository;

public class AuthorityUsecases {
    private final RoleRepository roleRepository;

    public AuthorityUsecases(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleEntity> rolesOfUser(String username) {
        return roleRepository.rolesOfUser(username);
    }
}
