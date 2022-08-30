package com.ssfMiniProject.BmiTrackerApp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ssfMiniProject.BmiTrackerApp.models.UserBmi;

@Service
public class UserRedis implements UserRepo {
    
    private static final Logger logger = LoggerFactory.getLogger(UserRedis.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate; 

    @Override
    public void save(final UserBmi userBmi) {
        redisTemplate.opsForValue().set(userBmi.getId(), userBmi);
    }

    @Override
    public UserBmi findById (final String userId) {
        UserBmi result = (UserBmi) redisTemplate.opsForValue().get(userId); 
        logger.info(">>>" + result.getEmail());
        return result; 
    }
}
