package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.UserDO;

public class UserRepoCustomImpl implements UserRepoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public List<UserDO> following(Long followerId, Long limit, Long offset) {
        var sql = "SELECT u.* FROM user_follow uf JOIN user u ON uf.following_id = u.id WHERE 1=1";
        if (followerId != null) {
            sql += " AND uf.follower_id = :follower_id";
        }
        sql += " ORDER BY uf.created_at DESC";
        if (limit != null) {
            sql += " LIMIT :limit";
        }
        if (offset != null) {
            sql += " OFFSET :offset";
        }

        var query = entityManager.createNativeQuery(sql, UserDO.class);
        if (followerId != null) {
            query.setParameter("follower_id", followerId);
        }
        if (limit != null) {
            query.setParameter("limit", limit);
        }
        if (offset != null) {
            query.setParameter("offset", offset);
        }

        @SuppressWarnings("unchecked")
        var postEntities = (List<UserDO>) query.getResultList();
        return postEntities;
    }

    public Long followingCount(Long followerId) {
        var sql = "SELECT COUNT(*) FROM user_follow uf WHERE 1=1";
        if (followerId != null) {
            sql += " AND uf.follower_id = :follower_id";
        }

        var query = entityManager.createNativeQuery(sql, UserDO.class);
        if (followerId != null) {
            query.setParameter("follower_id", followerId);
        }

        return (Long) query.getSingleResult();
    }

    public List<UserDO> follower(Long followingId, Long limit, Long offset) {
        var sql = "SELECT u.* FROM user_follow uf JOIN user u ON uf.follower_id = u.id WHERE 1=1";
        if (followingId != null) {
            sql += " AND uf.following_id = :following_id";
        }
        sql += " ORDER BY uf.created_at DESC";
        if (limit != null) {
            sql += " LIMIT :limit";
        }
        if (offset != null) {
            sql += " OFFSET :offset";
        }

        var query = entityManager.createNativeQuery(sql, UserDO.class);
        if (followingId != null) {
            query.setParameter("following_id", followingId);
        }
        if (limit != null) {
            query.setParameter("limit", limit);
        }
        if (offset != null) {
            query.setParameter("offset", offset);
        }

        @SuppressWarnings("unchecked")
        var postEntities = (List<UserDO>) query.getResultList();
        return postEntities;
    }

    public Long followerCount(Long followingId) {
        var sql = "SELECT COUNT(*) FROM user_follow uf WHERE 1=1";
        if (followingId != null) {
            sql += " AND uf.following_id = :following_id";
        }

        var query = entityManager.createNativeQuery(sql, UserDO.class);
        if (followingId != null) {
            query.setParameter("following_id", followingId);
        }

        return (Long) query.getSingleResult();
    }
}
