package net.jaggerwang.sbip.adapter.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserRoleDo;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRoleDo, Long>, QuerydslPredicateExecutor<UserRoleDo> {
}
