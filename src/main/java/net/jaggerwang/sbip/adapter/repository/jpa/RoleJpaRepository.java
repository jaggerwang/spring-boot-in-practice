package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.RoleDo;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleDo, Long>, QuerydslPredicateExecutor<RoleDo> {
        Optional<RoleDo> findByName(String name);
}
