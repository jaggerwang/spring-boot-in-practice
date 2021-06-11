package net.jaggerwang.sbip.usecase.port.dao;

import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.PostBO;

/**
 * @author Jagger Wang
 */
public interface PostDao {
    /**
     * 保存帖子
     * @param postBO 要保存的帖子对象
     * @return 已保存的帖子对象
     */
    PostBO save(PostBO postBO);

    /**
     * 删除帖子
     * @param id 帖子 ID
     */
    void delete(Long id);

    /**
     * 查找指定 ID 的帖子
     * @param id 帖子 ID
     * @return 帖子
     */
    Optional<PostBO> findById(Long id);

    /**
     * 查找某个用户发布的帖子
     * @param userId 用户 ID，若为 Null 则查找所有用户
     * @param limit 返回数量
     * @param offset 起始位置
     * @return 帖子列表
     */
    List<PostBO> published(Long userId, Long limit, Long offset);

    /**
     * 统计某个用户发布的帖子数量
     * @param userId 用户 ID，若为 Null 则查找所有用户
     * @return 帖子数量
     */
    Long publishedCount(Long userId);

    /**
     * 收藏某个帖子
     * @param userId 用户 ID
     * @param postId 帖子 ID
     */
    void like(Long userId, Long postId);

    /**
     * 取消收藏某个帖子
     * @param userId 用户 ID
     * @param postId 帖子 ID
     */
    void unlike(Long userId, Long postId);

    /**
     * 查找某个用户收藏的帖子
     * @param userId 用户 ID
     * @param limit 返回数量
     * @param offset 起始位置
     * @return 帖子列表
     */
    List<PostBO> liked(Long userId, Long limit, Long offset);

    /**
     * 统计某个用户收藏的帖子数量
     * @param userId 用户 ID
     * @return 帖子数量
     */
    Long likedCount(Long userId);

    /**
     * 查找某个用户关注的人所发布的帖子，按帖子 ID 倒序，可筛选某个帖子之前和之后的
     * @param userId 用户 ID
     * @param limit 返回数量
     * @param beforeId 小于帖子 ID
     * @param afterId 大于帖子 ID
     * @return 帖子列表
     */
    List<PostBO> following(Long userId, Long limit, Long beforeId, Long afterId);

    /**
     * 统计某个用户关注的人所发布的帖子数量
     * @param userId 用户 ID
     * @return 帖子数量
     */
    Long followingCount(Long userId);

    /**
     * 是否收藏过某个帖子
     * @param userId 用户 ID
     * @param postId 帖子 ID
     * @return 是否收藏
     */
    Boolean isLiked(Long userId, Long postId);
}
