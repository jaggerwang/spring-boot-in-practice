package net.jaggerwang.sbip.adapter.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.jaggerwang.sbip.adapter.repository.jpa.entity.PostDO;

public class PostRepoCustomImpl implements PostRepoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PostDO> published(Long userId, Long limit, Long offset) {
        var sql = "SELECT * FROM post p WHERE 1=1";
        if (userId != null) {
            sql += " AND p.user_id = :user_id";
        }
        sql += " ORDER BY created_at DESC";
        if (limit != null) {
            sql += " LIMIT :limit";
        }
        if (offset != null) {
            sql += " OFFSET :offset";
        }

        var query = entityManager.createNativeQuery(sql, PostDO.class);
        if (userId != null) {
            query.setParameter("user_id", userId);
        }
        if (limit != null) {
            query.setParameter("limit", limit);
        }
        if (offset != null) {
            query.setParameter("offset", offset);
        }

        @SuppressWarnings("unchecked")
        var postEntities = (List<PostDO>) query.getResultList();
        return postEntities;
    }

    public Long publishedCount(Long userId) {
        var sql = "SELECT COUNT(*) FROM post p WHERE 1=1";
        if (userId != null) {
            sql += " AND p.user_id = :user_id";
        }

        var query = entityManager.createNativeQuery(sql, PostDO.class);
        if (userId != null) {
            query.setParameter("user_id", userId);
        }

        return (Long) query.getSingleResult();
    }

    @Override
    public List<PostDO> liked(Long userId, Long limit, Long offset) {
        var sql = "SELECT p.* FROM post_like pl JOIN post p ON pl.post_id = p.id WHERE 1=1";
        if (userId != null) {
            sql += " AND pl.user_id = :user_id";
        }
        sql += " ORDER BY p.created_at DESC";
        if (limit != null) {
            sql += " LIMIT :limit";
        }
        if (offset != null) {
            sql += " OFFSET :offset";
        }

        var query = entityManager.createNativeQuery(sql, PostDO.class);
        if (userId != null) {
            query.setParameter("user_id", userId);
        }
        if (limit != null) {
            query.setParameter("limit", limit);
        }
        if (offset != null) {
            query.setParameter("offset", offset);
        }

        @SuppressWarnings("unchecked")
        var postEntities = (List<PostDO>) query.getResultList();
        return postEntities;
    }

    @Override
    public Long likedCount(Long userId) {
        var sql = "SELECT COUNT(*) FROM post_like pl WHERE 1=1";
        if (userId != null) {
            sql += " AND pl.user_id = :user_id";
        }

        var query = entityManager.createNativeQuery(sql, PostDO.class);
        if (userId != null) {
            query.setParameter("user_id", userId);
        }

        return (Long) query.getSingleResult();
    }

    @Override
    public List<PostDO> following(Long userId, Long limit, Long beforeId, Long afterId) {
        var sql = "SELECT p.* FROM post p JOIN user_follow uf ON p.user_id = uf.following_id "
                + "WHERE uf.follower_id = :user_id";
        if (beforeId != null) {
            sql += " AND p.id < :before_id";
        }
        if (afterId != null) {
            sql += " AND p.id > :after_id";
        }
        sql += " ORDER BY p.id DESC";
        if (limit != null) {
            sql += " LIMIT :limit";
        }

        var query = entityManager.createNativeQuery(sql, PostDO.class);
        query.setParameter("user_id", userId);
        if (beforeId != null) {
            query.setParameter("before_id", beforeId);
        }
        if (afterId != null) {
            query.setParameter("after_id", afterId);
        }
        if (limit != null) {
            query.setParameter("limit", limit);
        }

        @SuppressWarnings("unchecked")
        var postEntities = (List<PostDO>) query.getResultList();
        return postEntities;
    }

    @Override
    public Long followingCount(Long userId) {
        var sql = "SELECT COUNT(*) FROM post p JOIN user_follow uf ON p.user_id = uf.following_id "
                + "WHERE uf.follower_id = :user_id";
        var query = entityManager.createNativeQuery(sql, Long.class);
        query.setParameter("user_id", userId);

        return (Long) query.getSingleResult();
    }
}
