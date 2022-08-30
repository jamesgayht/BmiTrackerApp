package com.ssfMiniProject.BmiTrackerApp.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    //define your redis information in application.properties

    //inject the values into the class
    @Value("${spring.redis.host}")
    private String redisHost; 

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort; 

    @Value("${spring.redis.password}")
    private String redisPassword; 

    //create a method that returns an instance of redis method
    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort.get());
        redisStandaloneConfiguration.setPassword(redisPassword);

        //use the jedis driver to build the confirguration
        final JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().build(); 
        final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration); 
        jedisConnectionFactory.afterPropertiesSet();
        
        //string interpolate the variable for checking 
        logger.info("redis host port > {redisHost} {redisPort}", redisHost, redisPort);

        //lastly instantiate the redis template 
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>(); 
        //set the factory in, key initializers, so that whenver we create a record the key values is recognized in the redis database
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //create a custom serializer that is able to store any type
        RedisSerializer<Object> redisSerializer = new JdkSerializationRedisSerializer(getClass().getClassLoader()); 
        redisTemplate.setValueSerializer(redisSerializer);
        return redisTemplate; 
    }
}
