package net.jaggerwang.sbip.adapter.dao;

import java.util.Optional;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.UserStatRepository;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.UserStat;
import net.jaggerwang.sbip.entity.UserStatBO;
import net.jaggerwang.sbip.usecase.port.dao.UserStatDAO;

/**
 * @author Jagger Wang
 */
@Component
public class UserStatDAOImpl implements UserStatDAO {
    private UserStatRepository userStatJpaRepo;

    public UserStatDAOImpl(UserStatRepository userStatJpaRepo) {
        this.userStatJpaRepo = userStatJpaRepo;
    }

    @Override
    public UserStatBO save(UserStatBO userStatBO) {
        return userStatJpaRepo.save(UserStat.fromEntity(userStatBO)).toEntity();
    }

    @Override
    public Optional<UserStatBO> findById(Long id) {
        return userStatJpaRepo.findById(id).map(userStat -> userStat.toEntity());
    }

    @Override
    public Optional<UserStatBO> findByUserId(Long userId) {
        return userStatJpaRepo.findByUserId(userId).map(userStat -> userStat.toEntity());
    }
}
