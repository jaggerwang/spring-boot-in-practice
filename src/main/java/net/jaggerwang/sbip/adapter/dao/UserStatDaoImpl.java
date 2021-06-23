package net.jaggerwang.sbip.adapter.dao;

import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        var userStat = userStatMapper.selectById(userStatBO.getId());
        if (userStat == null) {
            userStat  = UserStat.fromBO(userStatBO);
            userStatMapper.insert(userStat);
        } else {
            userStatMapper.updateById(userStat);
        }
        return userStat.toBO();
    }

    @Override
    public Optional<UserStatBO> findById(Long id) {
        return Optional.ofNullable(userStatMapper.selectById(id)).map(UserStat::toBO);
    }

    @Override
    public Optional<UserStatBO> findByUserId(Long userId) {
        var queryWrapper = new QueryWrapper<UserStat>();
        queryWrapper.eq("user_id", userId);
        return Optional.ofNullable(userStatMapper.selectOne(queryWrapper)).map(UserStat::toBO);
    }
}
