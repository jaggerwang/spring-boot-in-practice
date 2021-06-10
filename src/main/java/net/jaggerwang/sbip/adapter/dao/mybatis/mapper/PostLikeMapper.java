package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.PostLike;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jagger Wang
 */
@Mapper
public interface PostLikeMapper {
    /**
     * 插入动态喜欢
     * @param postLike
     */
    void insert(PostLike postLike);

    /**
     * 更新动态喜欢
     * @param postLike
     */
    void update(PostLike postLike);

    /**
     * 删除动态喜欢
     * @param id
     */
    void delete(Long id);

    /**
     * 根据用户 ID 和动态 ID 删除动态喜欢
     * @param userId
     * @param postId
     */
    void deleteByUserIdAndPostId(Long userId, Long postId);

    /**
     * 查询动态喜欢
     * @param id
     * @return
     */
    PostLike select(Long id);

    /**
     * 根据用户和动态查询动态喜欢
     * @param userId
     * @param postId
     * @return
     */
    PostLike selectByUserIdAndPostId(Long userId, Long postId);
}
