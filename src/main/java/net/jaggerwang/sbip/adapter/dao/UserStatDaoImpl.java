package net.jaggerwang.sbip.adapter.dao;

import java.util.Optional;

import net.jaggerwang.sbip.adapter.dao.mybatis.mapper.UserStatMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.UserStat;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.UserStatBO;
import net.jaggerwang.sbip.usecase.port.dao.UserStatDao;

/**
 * @author Jagger Wang
 */
@Component
public class UserStatDaoImpl extends AbstractDao implements UserStatDao {
    private final UserStatMapper userStatMapper;

    public UserStatDaoImpl(UserStatMapper userStatMapper) {
        this.userStatMapper = userStatMapper;
    }

    @Override
    public UserStatBO save(UserStatBO userStatBO) {
        var userStat = userStatMapper.select(userStatBO.getId());
        if (userStat == null) {
            userStat  = UserStat.fromBO(userStatBO);
            userStatMapper.insert(userStat);
        } else {
            userStatMapper.update(userStat);
        }
        return userStat.toBO();
    }

    @Override
    public Optional<UserStatBO> findById(Long id) {
        return Optional.ofNullable(userStatMapper.select(id)).map(UserStat::toBO);
    }

    @Override
    public Optional<UserStatBO> findByUserId(Long userId) {
        return Optional.ofNullable(userStatMapper.selectByUserId(userId)).map(UserStat::toBO);
    }
}
