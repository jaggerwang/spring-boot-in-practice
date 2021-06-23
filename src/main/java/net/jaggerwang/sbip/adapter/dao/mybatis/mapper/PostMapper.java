package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jagger Wang
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    /**
     * 查询某个用户喜欢的动态
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    List<Post> selectLiked(Long userId, Long limit, Long offset);

    /**
     * 查询某个用户关注的用户发布的动态
     * @param userId
     * @param limit
     * @param beforeId
     * @param afterId
     * @return
     */
    List<Post> selectFollowing(Long userId, Long limit, Long beforeId, Long afterId);

    /**
     * 查询某个用户关注的用户发布的动态数
     * @param userId
     * @return
     */
    Long selectFollowingCount(Long userId);
}
