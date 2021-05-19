package com.example.demo.service.common;

import com.example.demo.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile("!local")
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public Object getObject(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public String getString(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return String.valueOf(Optional.ofNullable(valueOperations.get(key)).orElse(""));
    }

    public void setObject(String key, Object obj) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if(obj instanceof User) {
            User user = objectMapper.convertValue(obj, User.class);
            valueOperations.set(key, user);
        } else {
            valueOperations.set(key, obj);
        }
    }

    public void setString(String key, String value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void removeKey(String key) {
        redisTemplate.delete(key);
    }
}
