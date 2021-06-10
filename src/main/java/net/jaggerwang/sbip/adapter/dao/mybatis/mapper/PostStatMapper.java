package net.jaggerwang.sbip.adapter.dao.mybatis.mapper;

import net.jaggerwang.sbip.adapter.dao.mybatis.model.PostStat;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jagger Wang
 */
@Mapper
public interface PostStatMapper {
    /**
     * 插入动态统计
     * @param postStat
     */
    void insert(PostStat postStat);

    /**
     * 更新动态统计
     * @param postStat
     */
    void update(PostStat postStat);

    /**
     * 删除动态统计
     * @param id
     */
    void delete(Long id);

    /**
     * 查询动态统计
     * @param id
     * @return
     */
    PostStat select(Long id);

    /**
     * 根据动态 ID 查询动态统计
     * @param postId
     * @return
     */
    PostStat selectByPostId(Long postId);
}
