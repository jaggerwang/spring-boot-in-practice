package net.jaggerwang.sbip.adapter.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.User;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.UserRepository;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.QUser;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.QUserFollow;
import net.jaggerwang.sbip.adapter.dao.jpa.entity.UserFollow;
import net.jaggerwang.sbip.adapter.dao.jpa.repository.UserFollowRepository;
import net.jaggerwang.sbip.entity.UserBO;
import net.jaggerwang.sbip.usecase.port.dao.UserDAO;

/**
 * @author Jagger Wang
 */
@Component
public class UserDAOImpl implements UserDAO {
    private JPAQueryFactory jpaQueryFactory;
    private UserRepository userJpaRepo;
    private UserFollowRepository userFollowJpaRepo;

    public UserDAOImpl(JPAQueryFactory jpaQueryFactory, UserRepository userJpaRepo,
                       UserFollowRepository userFollowJpaRepo) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.userJpaRepo = userJpaRepo;
        this.userFollowJpaRepo = userFollowJpaRepo;
    }

    @Override
    public UserBO save(UserBO userBO) {
        return userJpaRepo.save(User.fromEntity(userBO)).toEntity();
    }

    @Override
    public Optional<UserBO> findById(Long id) {
        return userJpaRepo.findById(id).map(user -> user.toEntity());
    }

    @Override
    public Optional<UserBO> findByUsername(String username) {
        return userJpaRepo.findByUsername(username).map(user -> user.toEntity());
    }

    @Override
    public Optional<UserBO> findByEmail(String email) {
        return userJpaRepo.findByEmail(email).map(user -> user.toEntity());
    }

    @Override
    public Optional<UserBO> findByMobile(String mobile) {
        return userJpaRepo.findByMobile(mobile).map(user -> user.toEntity());
    }

    @Override
    public void follow(Long followerId, Long followingId) {
        userFollowJpaRepo.save(
                UserFollow.builder().followerId(followerId).followingId(followingId).build());
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {
        userFollowJpaRepo.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    private JPAQuery<User> followingQuery(Long followerId) {
        var query = jpaQueryFactory.selectFrom(QUser.user)
                .join(QUserFollow.userFollow).on(
                        QUser.user.id.eq(QUserFollow.userFollow.followingId));
        if (followerId != null) {
            query.where(QUserFollow.userFollow.followerId.eq(followerId));
        }
        return query;
    }

    @Override
    public List<UserBO> following(Long followerId, Long limit, Long offset) {
        var query = followingQuery(followerId);
        query.orderBy(QUserFollow.userFollow.id.desc());
        if (limit != null) {
            query.limit(limit);
        }
        if (offset != null) {
            query.offset(offset);
        }

        return query.fetch().stream().map(user -> user.toEntity()).collect(Collectors.toList());
    }

    @Override
    public Long followingCount(Long followerId) {
        return followingQuery(followerId).fetchCount();
    }

    private JPAQuery<User> followerQuery(Long followingId) {
        var query = jpaQueryFactory.selectFrom(QUser.user)
                .join(QUserFollow.userFollow).on(
                        QUser.user.id.eq(QUserFollow.userFollow.followerId));
        if (followingId != null) {
            query.where(QUserFollow.userFollow.followingId.eq(followingId));
        }
        return query;
    }

    @Override
    public List<UserBO> follower(Long followingId, Long limit, Long offset) {
        var query = followerQuery(followingId);
        query.orderBy(QUserFollow.userFollow.id.desc());
        if (limit != null) {
            query.limit(limit);
        }
        if (offset != null) {
            query.offset(offset);
        }

        return query.fetch().stream().map(user -> user.toEntity()).collect(Collectors.toList());
    }

    @Override
    public Long followerCount(Long followingId) {
        return followerQuery(followingId).fetchCount();
    }

    @Override
    public Boolean isFollowing(Long followerId, Long followingId) {
        return userFollowJpaRepo.existsByFollowerIdAndFollowingId(followerId, followingId);
    }
}
