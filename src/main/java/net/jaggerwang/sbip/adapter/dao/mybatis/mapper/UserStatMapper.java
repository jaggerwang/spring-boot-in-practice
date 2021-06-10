package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.UserStat;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jagger Wang
 */
@Mapper
public interface UserStatMapper {
    /**
     * 插入用户统计
     * @param userStat
     */
    void insert(UserStat userStat);

    /**
     * 更新用户统计
     * @param userStat
     */
    void update(UserStat userStat);

    /**
     * 删除用户统计
     * @param id
     */
    void delete(Long id);

    /**
     * 查询用户统计
     * @param id
     * @return
     */
    UserStat select(Long id);

    /**
     * 根据用户 ID 查询用户统计
     * @param userId
     * @return
     */
    UserStat selectByUserId(Long userId);
}
