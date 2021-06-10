package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.UserFollow;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jagger Wang
 */
@Mapper
public interface UserFollowMapper {
    /**
     * 插入用户关注
     * @param userFollow
     */
    void insert(UserFollow userFollow);

    /**
     * 更新用户关注
     * @param userFollow
     */
    void update(UserFollow userFollow);

    /**
     * 删除用户关注
     * @param id
     */
    void delete(Long id);

    /**
     * 删除用户关注
     * @param followerId
     * @param followingId
     */
    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    /**
     * 查询用户关注
     * @param id
     * @return
     */
    UserFollow select(Long id);

    /**
     * 根据关注者和被关注者查询用户关注
     * @param followerId
     * @param followingId
     * @return
     */
    UserFollow selectByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
