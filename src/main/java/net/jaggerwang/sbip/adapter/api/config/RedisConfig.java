package net.jaggerwang.sbip.adapter.api.config;

import java.io.Serializable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author Jagger Wang
 */
@Configuration(proxyBeanMethods = false)
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(
            LettuceConnectionFactory connectionFactory) {
        var redisTemplate = new RedisTemplate<String, Serializable>();

        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        return redisTemplate;
    }
}
