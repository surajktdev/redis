package com.api.redis.dao;

import com.api.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "USER";

    // save user

    public User save(User user){
        redisTemplate.opsForHash().put(KEY, user.getUserId(), user);
        return user;
    }

    // get user
    public User get(String id){
        return (User) redisTemplate.opsForHash().get(KEY, id);
    }

    // find all
    public Map<Object, Object> findAll(){
        return redisTemplate.opsForHash().entries(KEY);
    }

    // delete
    public void delete(String id){
        redisTemplate.opsForHash().delete(KEY, id);
    }
}
