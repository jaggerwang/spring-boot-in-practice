package net.jaggerwang.sbip.adapter.repository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.PostStatRepo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostStatDO;
import net.jaggerwang.sbip.entity.PostStatEntity;
import net.jaggerwang.sbip.usecase.port.repository.PostStatRepository;

@Component
public class PostStatRepositoryImpl implements PostStatRepository {
    @Autowired
    private PostStatRepo postStatRepo;

    @Override
    public PostStatEntity save(PostStatEntity postStatEntity) {
        return postStatRepo.save(PostStatDO.fromEntity(postStatEntity)).toEntity();
    }

    @Override
    public Optional<PostStatEntity> findById(Long id) {
        return postStatRepo.findById(id).map(postStatDO -> postStatDO.toEntity());
    }

    @Override
    public Optional<PostStatEntity> findByPostId(Long postId) {
        return postStatRepo.findByPostId(postId).map(postStatDO -> postStatDO.toEntity());
    }
}
