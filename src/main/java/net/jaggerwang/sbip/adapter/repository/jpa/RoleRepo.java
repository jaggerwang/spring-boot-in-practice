package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.RoleDO;

@Repository
public interface RoleRepo extends JpaRepository<RoleDO, Long> {
        Optional<RoleDO> findByName(String name);
}
