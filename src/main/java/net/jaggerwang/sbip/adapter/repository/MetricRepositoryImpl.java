package net.jaggerwang.sbip.adapter.repository;

import java.io.Serializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import net.jaggerwang.sbip.entity.MetricEntity;
import net.jaggerwang.sbip.usecase.port.repository.MetricRepository;

@Component
public class MetricRepositoryImpl implements MetricRepository {
    private static final String key = "sbip:metric";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public long increment(String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public MetricEntity get() {
        var map = redisTemplate.opsForHash().entries(key);
        return objectMapper.convertValue(map, MetricEntity.class);
    }
}
