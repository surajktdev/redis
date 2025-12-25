package com.api.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplet = new RedisTemplate<>();

        // connection factory
        redisTemplet.setConnectionFactory(connectionFactory());

        // key serializer
        redisTemplet.setKeySerializer(new StringRedisSerializer());

        // value serializer
        ObjectMapper mapper = new ObjectMapper();
        redisTemplet.setValueSerializer(new GenericJacksonJsonRedisSerializer(mapper));

        return redisTemplet;
    }
}
