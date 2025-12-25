package com.api.redis.ttl;

import com.api.redis.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ttl/users")
public class UserTTLController {

    private final UserCacheService cacheService;

    public UserTTLController(UserCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return cacheService.save(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable String id) {
        return cacheService.get(id);
    }

    @GetMapping("/{id}/sliding")
    public User getWithSlidingTTL(@PathVariable String id) {
        return cacheService.getWithSlidingTTL(id);
    }

    @GetMapping("/{id}/ttl")
    public Long ttl(@PathVariable String id) {
        return cacheService.getTTL(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        cacheService.delete(id);
    }
}
