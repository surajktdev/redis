package com.api.redis.ttl;

import com.api.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
public class UserCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final long USER_TTL_MINUTES = 50;

    public UserCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Save user with TTL
    public User save(User user) {
        String key = RedisKeyUtil.userKey(user.getUserId());

        redisTemplate.opsForValue().set(
                key,
                user,
                USER_TTL_MINUTES,
                TimeUnit.SECONDS
        );

        return user;
    }

    // Get user (TTL auto checked)
    public User get(String userId) {
        String key = RedisKeyUtil.userKey(userId);
//        return (User) redisTemplate.opsForValue().get(key);
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null; // cache miss
        }

        return objectMapper.convertValue(value, User.class);

    }

    // Sliding TTL (TTL refresh on access)
    public User getWithSlidingTTL(String userId) {
        String key = RedisKeyUtil.userKey(userId);

        User user = (User) redisTemplate.opsForValue().get(key);

        if (user != null) {
            redisTemplate.expire(key, USER_TTL_MINUTES, TimeUnit.MINUTES);
        }

        return user;
    }

    // Get remaining TTL (debug)
    public Long getTTL(String userId) {
        String key = RedisKeyUtil.userKey(userId);
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    // Delete user
    public void delete(String userId) {
        String key = RedisKeyUtil.userKey(userId);
        redisTemplate.delete(key);
    }

}
