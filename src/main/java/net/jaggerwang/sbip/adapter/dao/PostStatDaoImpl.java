package net.jaggerwang.sbip.adapter.dao;

import java.util.Optional;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.PostStatRepository;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.PostStat;
import net.jaggerwang.sbip.entity.PostStatBO;
import net.jaggerwang.sbip.usecase.port.dao.PostStatDao;

/**
 * @author Jagger Wang
 */
@Component
public class PostStatDaoImpl implements PostStatDao {
    private PostStatRepository postStatRepository;

    public PostStatDaoImpl(PostStatRepository postStatRepository) {
        this.postStatRepository = postStatRepository;
    }

    @Override
    public PostStatBO save(PostStatBO postStatBO) {
        return postStatRepository.save(PostStat.fromBO(postStatBO)).toBO();
    }

    @Override
    public Optional<PostStatBO> findById(Long id) {
        return postStatRepository.findById(id).map(postStat -> postStat.toBO());
    }

    @Override
    public Optional<PostStatBO> findByPostId(Long postId) {
        return postStatRepository.findByPostId(postId).map(postStat -> postStat.toBO());
    }
}
