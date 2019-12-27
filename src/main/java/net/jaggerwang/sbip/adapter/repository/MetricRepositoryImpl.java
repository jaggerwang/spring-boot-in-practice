package net.jaggerwang.sbip.adapter.repository;

import java.io.Serializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.MetricEntity;
import net.jaggerwang.sbip.usecase.port.repository.MetricRepository;

@Component
public class MetricRepositoryImpl implements MetricRepository {
    private static final String key = "sbip:metric";

    private RedisTemplate<String, Serializable> redisTemplate;
    private ObjectMapper objectMapper;

    public MetricRepositoryImpl(RedisTemplate<String, Serializable> redisTemplate,
            ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Long increment(String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public MetricEntity get() {
        var map = redisTemplate.opsForHash().entries(key);
        return objectMapper.convertValue(map, MetricEntity.class);
    }
}
