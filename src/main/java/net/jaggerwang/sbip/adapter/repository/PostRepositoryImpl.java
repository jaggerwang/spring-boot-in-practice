package net.jaggerwang.sbip.adapter.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.repository.jpa.PostRepo;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostDO;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostLikeDO;
import net.jaggerwang.sbip.adapter.repository.jpa.PostLikeRepo;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.usecase.port.repository.PostRepository;

@Component
public class PostRepositoryImpl implements PostRepository {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private PostLikeRepo postLikeRepo;

    @Override
    public PostEntity save(PostEntity postEntity) {
        return postRepo.save(PostDO.fromEntity(postEntity)).toEntity();
    }

    @Override
    public void delete(Long id) {
        postRepo.deleteById(id);
    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return postRepo.findById(id).map(postDO -> postDO.toEntity());
    }

    @Override
    public List<PostEntity> published(Long userId, Long limit, Long offset) {
        return postRepo.published(userId, limit, offset).stream().map(fileDO -> fileDO.toEntity())
                .collect(Collectors.toList());
    }

    @Override
    public Long publishedCount(Long userId) {
        return postRepo.publishedCount(userId);
    }

    @Override
    public void like(Long userId, Long postId) {
        postLikeRepo.save(PostLikeDO.builder().userId(userId).postId(postId).build());
    }

    @Override
    public void unlike(Long userId, Long postId) {
        postLikeRepo.deleteByUserIdAndPostId(userId, postId);
    }

    @Override
    public List<PostEntity> liked(Long userId, Long limit, Long offset) {
        return postRepo.liked(userId, limit, offset).stream().map(fileDO -> fileDO.toEntity())
                .collect(Collectors.toList());
    }

    @Override
    public Long likedCount(Long userId) {
        return postRepo.likedCount(userId);
    }

    @Override
    public List<PostEntity> following(Long userId, Long limit, Long beforeId, Long afterId) {
        return postRepo.following(userId, limit, beforeId, afterId).stream()
                .map(fileDO -> fileDO.toEntity()).collect(Collectors.toList());
    }

    @Override
    public Long followingCount(Long userId) {
        return postRepo.followingCount(userId);
    }

    @Override
    public boolean isLiked(Long userId, Long postId) {
        return postLikeRepo.isLiked(userId, postId);
    }
}
