package com.api.redis.controller;

import com.api.redis.dao.UserDao;
import com.api.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @PostMapping
    public User createUSer(@RequestBody User user){
        user.setUserId(UUID.randomUUID().toString());
        return userDao.save(user);
    }

    @GetMapping("/{user-id}")
    public User getUser(@PathVariable("user-id") String userId){
        return userDao.get(userId);
    }

    @GetMapping
    public Map<Object, Object> getALl(){
        return userDao.findAll();
    }

    @DeleteMapping("/{user-id}")
    public void delete(@PathVariable("user-id") String userId){
        userDao.delete(userId);
    }
}
