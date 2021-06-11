package net.jaggerwang.sbip.adapter.dao;

import java.io.Serializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.MetricBO;
import net.jaggerwang.sbip.usecase.port.dao.MetricDao;

/**
 * @author Jagger Wang
 */
@Component
public class MetricDaoImpl implements MetricDao {
    private static final String KEY = "sbip:metric";

    private RedisTemplate<String, Serializable> redisTemplate;
    private ObjectMapper objectMapper;

    public MetricDaoImpl(RedisTemplate<String, Serializable> redisTemplate,
                         ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Long increment(String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(KEY, hashKey, delta);
    }

    @Override
    public MetricBO get() {
        var map = redisTemplate.opsForHash().entries(KEY);
        return objectMapper.convertValue(map, MetricBO.class);
    }
}
