package net.jaggerwang.sbip.adapter.dao;

import java.util.Optional;

import net.jaggerwang.sbip.adapter.dao.mybatis.mapper.PostStatMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.PostStat;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.PostStatBO;
import net.jaggerwang.sbip.usecase.port.dao.PostStatDao;

/**
 * @author Jagger Wang
 */
@Component
public class PostStatDaoImpl extends AbstractDao implements PostStatDao {
    private final PostStatMapper postStatMapper;

    public PostStatDaoImpl(PostStatMapper postStatMapper) {
        this.postStatMapper = postStatMapper;
    }

    @Override
    public PostStatBO save(PostStatBO postStatBO) {
        var postStat = postStatMapper.select(postStatBO.getId());
        if (postStat == null) {
            postStat  = PostStat.fromBO(postStatBO);
            postStatMapper.insert(postStat);
        } else {
            postStatMapper.update(postStat);
        }
        return postStat.toBO();
    }

    @Override
    public Optional<PostStatBO> findById(Long id) {
        return Optional.ofNullable(postStatMapper.select(id)).map(PostStat::toBO);
    }

    @Override
    public Optional<PostStatBO> findByPostId(Long postId) {
        return Optional.ofNullable(postStatMapper.selectByPostId(postId)).map(PostStat::toBO);
    }
}
