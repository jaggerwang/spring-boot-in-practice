package net.jaggerwang.sbip.adapter.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.PostRepository;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.Post;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.PostLike;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.QPost;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.QPostLike;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.QUserFollow;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.PostLikeRepository;
import net.jaggerwang.sbip.entity.PostBO;
import net.jaggerwang.sbip.usecase.port.dao.PostDao;

/**
 * @author Jagger Wang
 */
@Component
public class PostDaoImpl implements PostDao {
    private JPAQueryFactory jpaQueryFactory;
    private PostRepository postRepository;
    private PostLikeRepository postLikeRepository;

    public PostDaoImpl(JPAQueryFactory jpaQueryFactory, PostRepository postRepository,
                       PostLikeRepository postLikeRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
    }

    @Override
    public PostBO save(PostBO postBO) {
        return postRepository.save(Post.fromBO(postBO)).toBO();
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Optional<PostBO> findById(Long id) {
        return postRepository.findById(id).map(post -> post.toBO());
    }

    private JPAQuery<Post> publishedQuery(Long userId) {
        var query = jpaQueryFactory.selectFrom(QPost.post);
        if (userId != null) {
            query.where(QPost.post.userId.eq(userId));
        }
        return query;
    }

    @Override
    public List<PostBO> published(Long userId, Long limit, Long offset) {
        var query = publishedQuery(userId);
        query.orderBy(QPost.post.id.desc());
        if (limit != null) {
            query.limit(limit);
        }
        if (offset != null) {
            query.offset(offset);
        }

        return query.fetch().stream().map(post -> post.toBO()).collect(Collectors.toList());
    }

    @Override
    public Long publishedCount(Long userId) {
        return publishedQuery(userId).fetchCount();
    }

    @Override
    public void like(Long userId, Long postId) {
        postLikeRepository.save(PostLike.builder().userId(userId).postId(postId).build());
    }

    @Override
    public void unlike(Long userId, Long postId) {
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }

    private JPAQuery<Post> likedQuery(Long userId) {
        var query = jpaQueryFactory.selectFrom(QPost.post)
                .join(QPostLike.postLike).on(QPost.post.id.eq(QPostLike.postLike.postId));
        if (userId != null) {
            query.where(QPostLike.postLike.userId.eq(userId));
        }
        return query;
    }

    @Override
    public List<PostBO> liked(Long userId, Long limit, Long offset) {
        var query = likedQuery(userId);
        query.orderBy(QPostLike.postLike.id.desc());
        if (limit != null) {
            query.limit(limit);
        }
        if (offset != null) {
            query.offset(offset);
        }

        return query.fetch().stream().map(post -> post.toBO()).collect(Collectors.toList());
    }

    @Override
    public Long likedCount(Long userId) {
        return likedQuery(userId).fetchCount();
    }

    private JPAQuery<Post> followingQuery(Long userId) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .join(QUserFollow.userFollow).on(
                        QPost.post.userId.eq(QUserFollow.userFollow.followingId))
                .where(QUserFollow.userFollow.followerId.eq(userId));
    }

    @Override
    public List<PostBO> following(Long userId, Long limit, Long beforeId, Long afterId) {
        var query = followingQuery(userId);
        query.orderBy(QPost.post.id.desc());
        if (limit != null) {
            query.limit(limit);
        }
        if (beforeId != null) {
            query.where(QPost.post.id.lt(beforeId));
        }
        if (afterId != null) {
            query.where(QPost.post.id.gt(afterId));
        }

        return query.fetch().stream().map(post -> post.toBO()).collect(Collectors.toList());
    }

    @Override
    public Long followingCount(Long userId) {
        return followingQuery(userId).fetchCount();
    }

    @Override
    public Boolean isLiked(Long userId, Long postId) {
        return postLikeRepository.existsByUserIdAndPostId(userId, postId);
    }
}
