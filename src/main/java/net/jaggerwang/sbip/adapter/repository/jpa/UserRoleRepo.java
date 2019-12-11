package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserRoleDO;
import net.jaggerwang.sbip.entity.RoleEntity;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRoleDO, Long> {
        @Query(value = "SELECT r.* FROM role r JOIN user_role ur ON r.id = ur.role_id "
                        + "WHERE ur.user_id = :user_id ORDER BY ur.id", nativeQuery = true)
        List<RoleEntity> rolesOfUser(@Param("user_id") Long userId);

        @Query(value = "SELECT r.* FROM role r JOIN user_role ur ON r.id = ur.role_id "
                        + "JOIN user u ON ur.user_id = u.id WHERE u.username = :username "
                        + "ORDER BY ur.id", nativeQuery = true)
        List<RoleEntity> rolesOfUser(@Param("username") String username);
}
