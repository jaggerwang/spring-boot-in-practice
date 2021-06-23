package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jagger Wang
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询某个用户的关注用户
     * @param followerId
     * @param limit
     * @param offset
     * @return
     */
    List<User> selectFollowing(Long followerId, Long limit, Long offset);

    /**
     * 查询某个用户的粉丝
     * @param followingId
     * @param limit
     * @param offset
     * @return
     */
    List<User> selectFollower(Long followingId, Long limit, Long offset);
}
