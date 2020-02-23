package net.jaggerwang.sbip.usecase;

import java.util.List;
import java.util.Optional;

import net.jaggerwang.sbip.entity.PostEntity;
import net.jaggerwang.sbip.usecase.port.repository.PostRepository;

public class PostUsecase {
    private final PostRepository postRepository;

    public PostUsecase(PostRepository postRepository) {
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

    public Optional<PostEntity> info(Long id) {
        return postRepository.findById(id);
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

    public Boolean isLiked(Long userId, Long postId) {
        return postRepository.isLiked(userId, postId);
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
}
