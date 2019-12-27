package net.jaggerwang.sbip.adapter.repository;

import java.util.Optional;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.UserStatJpaRepository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserStatDo;
import net.jaggerwang.sbip.entity.UserStatEntity;
import net.jaggerwang.sbip.usecase.port.repository.UserStatRepository;

@Component
public class UserStatRepositoryImpl implements UserStatRepository {
    private UserStatJpaRepository userStatJpaRepo;

    public UserStatRepositoryImpl(UserStatJpaRepository userStatJpaRepo) {
        this.userStatJpaRepo = userStatJpaRepo;
    }

    @Override
    public UserStatEntity save(UserStatEntity userStatEntity) {
        return userStatJpaRepo.save(UserStatDo.fromEntity(userStatEntity)).toEntity();
    }

    @Override
    public Optional<UserStatEntity> findById(Long id) {
        return userStatJpaRepo.findById(id).map(userStatDo -> userStatDo.toEntity());
    }

    @Override
    public Optional<UserStatEntity> findByUserId(Long userId) {
        return userStatJpaRepo.findByUserId(userId).map(userStatDo -> userStatDo.toEntity());
    }
}
