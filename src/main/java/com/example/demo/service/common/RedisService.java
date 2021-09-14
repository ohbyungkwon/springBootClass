package com.example.demo.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String getString(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return String.valueOf(Optional.ofNullable(valueOperations.get(key)).orElse(""));
    }

    public void setString(String key, String value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public Object getObject(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOperations.get(key)).orElse(new Object());
    }

    public void setObject(String key, Object obj) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, obj);
    }

    public void removeKey(String key) {
        redisTemplate.delete(key);
    }
}
