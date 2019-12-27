package net.jaggerwang.sbip.adapter.repository;

import java.util.Optional;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.PostStatJpaRepository;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostStatDo;
import net.jaggerwang.sbip.entity.PostStatEntity;
import net.jaggerwang.sbip.usecase.port.repository.PostStatRepository;

@Component
public class PostStatRepositoryImpl implements PostStatRepository {
    private PostStatJpaRepository postStatJpaRepo;

    public PostStatRepositoryImpl(PostStatJpaRepository postStatJpaRepo) {
        this.postStatJpaRepo = postStatJpaRepo;
    }

    @Override
    public PostStatEntity save(PostStatEntity postStatEntity) {
        return postStatJpaRepo.save(PostStatDo.fromEntity(postStatEntity)).toEntity();
    }

    @Override
    public Optional<PostStatEntity> findById(Long id) {
        return postStatJpaRepo.findById(id).map(postStatDo -> postStatDo.toEntity());
    }

    @Override
    public Optional<PostStatEntity> findByPostId(Long postId) {
        return postStatJpaRepo.findByPostId(postId).map(postStatDo -> postStatDo.toEntity());
    }
}
