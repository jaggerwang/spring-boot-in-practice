package net.jaggerwang.sbip.adapter.repository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.UserStatRepo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserStatDO;
import net.jaggerwang.sbip.entity.UserStatEntity;
import net.jaggerwang.sbip.usecase.port.repository.UserStatRepository;

@Component
public class UserStatRepositoryImpl implements UserStatRepository {
    @Autowired
    private UserStatRepo userStatRepo;

    @Override
    public UserStatEntity save(UserStatEntity userStatEntity) {
        return userStatRepo.save(UserStatDO.fromEntity(userStatEntity)).toEntity();
    }

    @Override
    public Optional<UserStatEntity> findById(Long id) {
        return userStatRepo.findById(id).map(userStatDO -> userStatDO.toEntity());
    }

    @Override
    public Optional<UserStatEntity> findByUserId(Long userId) {
        return userStatRepo.findByUserId(userId).map(userStatDO -> userStatDO.toEntity());
    }
}
