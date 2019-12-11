package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserDO;

@Repository
public interface UserRepo extends JpaRepository<UserDO, Long>, UserRepoCustom {
        Optional<UserDO> findByUsername(String username);

        Optional<UserDO> findByMobile(String mobile);

        Optional<UserDO> findByEmail(String email);
}
