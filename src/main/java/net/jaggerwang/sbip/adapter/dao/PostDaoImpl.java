package net.jaggerwang.sbip.adapter.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        var post = postMapper.selectById(postBO.getId());
        if (post == null) {
            post  = Post.fromBO(postBO);
            postMapper.insert(post);
        } else {
            postMapper.updateById(post);
        }
        return post.toBO();
    }

    @Override
    public void delete(Long id) {
        postMapper.deleteById(id);
    }

    @Override
    public Optional<PostBO> findById(Long id) {
        return Optional.ofNullable(postMapper.selectById(id)).map(Post::toBO);
    }

    @Override
    public List<PostBO> published(Long userId, Long limit, Long offset) {
        var page = new Page<Post>(offset / limit, limit);
        var queryWrapper = new QueryWrapper<Post>();
        queryWrapper.eq("user_id", userId);
        return postMapper.selectPage(page, queryWrapper).getRecords().stream()
                .map(Post::toBO).collect(Collectors.toList());
    }

    @Override
    public Long publishedCount(Long userId) {
        var queryWrapper = new QueryWrapper<Post>();
        queryWrapper.eq("user_id", userId);
        return Long.valueOf(postMapper.selectCount(queryWrapper));
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
        postLikeMapper.deleteByMap(Map.of(
                "user_id", userId,
                "post_id", postId
        ));
    }

    @Override
    public List<PostBO> liked(Long userId, Long limit, Long offset) {
        return postMapper.selectLiked(userId, limit, offset)
                .stream().map(Post::toBO).collect(Collectors.toList());
    }

    @Override
    public Long likedCount(Long userId) {
        var queryWrapper = new QueryWrapper<PostLike>();
        queryWrapper.eq("user_id", userId);
        return Long.valueOf(postLikeMapper.selectCount(queryWrapper));
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
        var queryWrapper = new QueryWrapper<PostLike>();
        queryWrapper.allEq(Map.of(
                "user_id", userId,
                "post_id", postId
        ));
        return postLikeMapper.selectOne(queryWrapper) != null;
    }
}
