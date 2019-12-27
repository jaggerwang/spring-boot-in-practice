package net.jaggerwang.sbip.usecase;

import java.util.List;
import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.port.repository.PostRepository;

public class PostUsecases {
    private final PostRepository postRepository;

    public PostUsecases(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostEntity publish(PostEntity postEntity) {
        var post = PostEntity.builder().userId(postEntity.getUserId()).type(postEntity.getType())
                .text(postEntity.getText()).imageIds(postEntity.getImageIds())
                .videoId(postEntity.getVideoId()).build();
        return postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.delete(id);
    }

    public PostEntity info(Long id) {
        var postEntity = postRepository.findById(id);
        if (!postEntity.isPresent()) {
            throw new NotFoundException("动态未找到");
        }

        return postEntity.get();
    }

    public List<PostEntity> published(Long userId, Long limit, Long offset) {
        return postRepository.published(userId, limit, offset);
    }

    public Long publishedCount(Long userId) {
        return postRepository.publishedCount(userId);
    }

    public void like(Long userId, Long postId) {
        postRepository.like(userId, postId);
    }

    public void unlike(Long userId, Long postId) {
        postRepository.unlike(userId, postId);
    }

    public List<PostEntity> liked(Long userId, Long limit, Long offset) {
        return postRepository.liked(userId, limit, offset);
    }

    public Long likedCount(Long userId) {
        return postRepository.likedCount(userId);
    }

    public List<PostEntity> following(Long userId, Long limit, Long beforeId, Long afterId) {
        return postRepository.following(userId, limit, beforeId, afterId);
    }

    public Long followingCount(Long userId) {
        return postRepository.followingCount(userId);
    }

    public Boolean isLiked(Long userId, Long postId) {
        return postRepository.isLiked(userId, postId);
    }
}
