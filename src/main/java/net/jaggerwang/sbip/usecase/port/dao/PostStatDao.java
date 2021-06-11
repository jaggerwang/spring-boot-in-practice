package net.jaggerwang.sbip.usecase.port.dao;

import java.util.Optional;

import net.jaggerwang.sbip.entity.PostStatBO;

/**
 * @author Jagger Wang
 */
public interface PostStatDao {
    /**
     * 保存帖子统计
     * @param postStatBO 要保存的帖子统计
     * @return 已保存的帖子统计
     */
    PostStatBO save(PostStatBO postStatBO);

    /**
     * 查找指定 ID 的帖子统计
     * @param id 帖子统计 ID
     * @return 帖子统计
     */
    Optional<PostStatBO> findById(Long id);

    /**
     * 查找指定帖子的统计
     * @param postId 帖子 ID
     * @return 帖子统计
     */
    Optional<PostStatBO> findByPostId(Long postId);
}
