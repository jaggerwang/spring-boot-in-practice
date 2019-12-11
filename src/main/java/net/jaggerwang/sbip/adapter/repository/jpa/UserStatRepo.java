package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserStatDO;

@Repository
public interface UserStatRepo extends JpaRepository<UserStatDO, Long> {
    Optional<UserStatDO> findByUserId(Long userId);
}
