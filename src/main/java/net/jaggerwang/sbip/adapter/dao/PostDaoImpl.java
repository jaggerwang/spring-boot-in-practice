package net.jaggerwang.sbip.adapter.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.jaggerwang.sbip.adapter.dao.mybatis.mapper.PostLikeMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.mapper.PostMapper;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.Post;
import net.jaggerwang.sbip.adapter.dao.mybatis.model.PostLike;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.PostBO;
import net.jaggerwang.sbip.usecase.port.dao.PostDao;

/**
 * @author Jagger Wang
 */
@Component
public class PostDaoImpl extends AbstractDao implements PostDao {
    private final PostMapper postMapper;
    private final PostLikeMapper postLikeMapper;

    public PostDaoImpl(PostMapper postMapper, PostLikeMapper postLikeMapper) {
        this.postMapper = postMapper;
        this.postLikeMapper = postLikeMapper;
    }

    @Override
    public PostBO save(PostBO postBO) {
        var post = postMapper.select(postBO.getId());
        if (post == null) {
            post  = Post.fromBO(postBO);
            postMapper.insert(post);
        } else {
            postMapper.update(post);
        }
        return post.toBO();
    }

    @Override
    public void delete(Long id) {
        postMapper.delete(id);
    }

    @Override
    public Optional<PostBO> findById(Long id) {
        return Optional.ofNullable(postMapper.select(id)).map(Post::toBO);
    }

    @Override
    public List<PostBO> published(Long userId, Long limit, Long offset) {
        return postMapper.selectPublished(userId, limit, offset).stream()
                .map(Post::toBO).collect(Collectors.toList());
    }

    @Override
    public Long publishedCount(Long userId) {
        return postMapper.selectPublishedCount(userId);
    }

    @Override
    public void like(Long userId, Long postId) {
        var postLike  = PostLike.builder()
                .userId(userId)
                .postId(postId)
                .build();
        postLikeMapper.insert(postLike);
    }

    @Override
    public void unlike(Long userId, Long postId) {
        postLikeMapper.deleteByUserIdAndPostId(userId, postId);
    }

    @Override
    public List<PostBO> liked(Long userId, Long limit, Long offset) {
        return postMapper.selectLiked(userId, limit, offset)
                .stream().map(Post::toBO).collect(Collectors.toList());
    }

    @Override
    public Long likedCount(Long userId) {
        return postMapper.selectLikedCount(userId);
    }

    @Override
    public List<PostBO> following(Long userId, Long limit, Long beforeId, Long afterId) {
        return postMapper.selectFollowing(userId, limit, beforeId, afterId)
                .stream().map(Post::toBO).collect(Collectors.toList());
    }

    @Override
    public Long followingCount(Long userId) {
        return postMapper.selectFollowingCount(userId);
    }

    @Override
    public Boolean isLiked(Long userId, Long postId) {
        return postLikeMapper.selectByUserIdAndPostId(userId, postId) != null;
    }
}
