package net.jaggerwang.sbip.adapter.dao;

import java.util.Optional;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.PostStatRepository;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.PostStat;
import net.jaggerwang.sbip.entity.PostStatBO;
import net.jaggerwang.sbip.usecase.port.dao.PostStatDAO;

/**
 * @author Jagger Wang
 */
@Component
public class PostStatDAOImpl implements PostStatDAO {
    private PostStatRepository postStatJpaRepo;

    public PostStatDAOImpl(PostStatRepository postStatJpaRepo) {
        this.postStatJpaRepo = postStatJpaRepo;
    }

    @Override
    public PostStatBO save(PostStatBO postStatBO) {
        return postStatJpaRepo.save(PostStat.fromEntity(postStatBO)).toEntity();
    }

    @Override
    public Optional<PostStatBO> findById(Long id) {
        return postStatJpaRepo.findById(id).map(postStat -> postStat.toEntity());
    }

    @Override
    public Optional<PostStatBO> findByPostId(Long postId) {
        return postStatJpaRepo.findByPostId(postId).map(postStat -> postStat.toEntity());
    }
}
