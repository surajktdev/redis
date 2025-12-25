package com.api.redis.ttl;

public class RedisKeyUtil {
    public static String userKey(String userId) {
        return "user:profile:" + userId;
    }
}
