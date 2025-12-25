# Redis Practice

This project is built to practice Redis as a caching layer in a backend service. It includes cache key design,
rule-based cache lookup, overwrite/update behavior, TTL handling, and testing cache hits vs database fallbacks.


# 📌 Redis CLI & Spring Boot (Spring Data Redis) – Complete Notes

---
## 🔹 Service & Connection

|Purpose|Command|
|---|---|
|Start Redis server|`sudo service redis-server start`|
|Connect to Redis CLI|`redis-cli`|
|Connect to Redis (host/port)|`redis-cli -h localhost -p 6379`|
|Check Redis is running|`PING` → `PONG`|
|Exit Redis CLI|`exit`|

---

## 🔹 Key-Level Commands

|Purpose|Command|
|---|---|
|List all keys (❌ prod)|`KEYS *`|
|List keys safely (✅ prod)|`SCAN 0`|
|Check key exists|`EXISTS user`|
|Check key type|`TYPE user`|
|Delete a key|`DEL user`|

---

## 🔹 Hash Commands (Primary Use Case ✅)

> **Used for entity caching (ID → Object mapping)**

|Purpose|Command|
|---|---|
|Get all entries|`HGETALL user`|
|Get single value|`HGET user <field>`|
|Get all fields (IDs)|`HKEYS user`|
|Get all values|`HVALS user`|
|Count entries|`HLEN user`|
|Delete a field|`HDEL user <field>`|
|Check field exists|`HEXISTS user <field>`|
|Increment value|`HINCRBY user count 1`|
|Scan hash safely|`HSCAN user 0`|

---

## 🔹 Database Maintenance

|Purpose|Command|
|---|---|
|Clear current DB|`FLUSHDB`|
|Clear all DBs|`FLUSHALL`|
|Select DB index|`SELECT 0`|

---

## 🔹 TTL & Expiration (Very Important ⭐)

|Purpose|Command|
|---|---|
|Set TTL on key|`EXPIRE user 60`|
|Check remaining TTL|`TTL user`|
|Remove TTL|`PERSIST user`|

### ⚠️ Important Limitation

> Redis **does NOT support TTL on individual hash fields**  
> TTL is applied **only at key level**

---

## 🧠 Redis Data Structures – When to Use What

|Requirement|Use|
|---|---|
|Key → Single value|`opsForValue()`|
|Key → Map / Object|`opsForHash()`|
|FIFO / LIFO|`opsForList()`|
|Unique values|`opsForSet()`|
|Ranking / Priority|`opsForZSet()`|
|Location-based|`opsForGeo()`|
|Large-scale counting|`opsForHyperLogLog()`|
|Event streaming|`opsForStream()`|

---

## 🔁 Spring Boot → Redis CLI Mapping (Hash)

|Spring Code|Redis CLI|
|---|---|
|`opsForHash().put()`|`HSET`|
|`opsForHash().putIfAbsent()`|`HSETNX`|
|`opsForHash().putAll()`|`HMSET`|
|`opsForHash().get()`|`HGET`|
|`opsForHash().multiGet()`|`HMGET`|
|`opsForHash().entries()`|`HGETALL`|
|`opsForHash().keys()`|`HKEYS`|
|`opsForHash().values()`|`HVALS`|
|`opsForHash().size()`|`HLEN`|
|`opsForHash().hasKey()`|`HEXISTS`|
|`opsForHash().delete()`|`HDEL`|
|`opsForHash().increment()`|`HINCRBY / HINCRBYFLOAT`|
|`opsForHash().scan()`|`HSCAN`|

---

## 🔑 Key-Level (Used With Hash)

|Spring Code|Redis CLI|
|---|---|
|`redisTemplate.expire()`|`EXPIRE`|
|`redisTemplate.getExpire()`|`TTL`|
|`redisTemplate.persist()`|`PERSIST`|
|`redisTemplate.delete()`|`DEL`|
|`redisTemplate.hasKey()`|`EXISTS`|
|`redisTemplate.type()`|`TYPE`|

---

## 🔁 Cache Usage Pattern (Industry Standard)

### Cache-Aside Pattern

1️⃣ Check cache  
2️⃣ Cache miss → Fetch from DB  
3️⃣ Store in Redis with TTL  
4️⃣ Return response

---

## 🧪 Quick Debug Flow

```bash
TYPE user
HLEN user
HGETALL user
TTL user
```

---

## 🔐 Serialization Note (Spring Boot Specific)

> Avoid default JDK serialization.  
> Prefer JSON for readability & compatibility.

**Recommended Serializer:**
since Spring Data Redis 4.x
```java
// value serializer
ObjectMapper mapper = new ObjectMapper();
GenericJacksonJsonRedisSerializer(mapper);
```

---

## ✅ Best Practices

- ❌ Avoid `KEYS *` in production

- ✅ Use `SCAN / HSCAN`

- ✅ Always apply TTL for cache data

- ✅ Hash is ideal for **UserId → User Object**

- ✅ Follow cache-aside pattern


---

## 🎯 Interview Golden Line

> “Spring Data Redis exposes Redis data structures via `opsForX()` APIs. In real systems, we primarily use Hashes for entity caching with TTL applied at key level, following the cache-aside pattern.”