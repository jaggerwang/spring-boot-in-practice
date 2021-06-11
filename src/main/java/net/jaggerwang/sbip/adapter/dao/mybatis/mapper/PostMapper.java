package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jagger Wang
 */
@Mapper
public interface PostMapper {
    /**
     * 插入动态
     * @param post
     */
    void insert(Post post);

    /**
     * 更新动态
     * @param post
     */
    void update(Post post);

    /**
     * 删除动态
     * @param id
     */
    void delete(Long id);

    /**
     * 查询动态
     * @param id
     * @return
     */
    Post select(Long id);

    /**
     * 查询某个用户发布的动态
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    List<Post> selectPublished(Long userId, Long limit, Long offset);

    /**
     * 查询某个用户发布的动态数
     * @param userId
     * @return
     */
    Long selectPublishedCount(Long userId);

    /**
     * 查询某个用户喜欢的动态
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    List<Post> selectLiked(Long userId, Long limit, Long offset);

    /**
     * 查询某个用户喜欢的动态数
     * @param userId
     * @return
     */
    Long selectLikedCount(Long userId);

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
