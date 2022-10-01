package com.ssfMiniProject.BmiTrackerApp.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssfMiniProject.BmiTrackerApp.models.User;
import com.ssfMiniProject.BmiTrackerApp.services.BmiGeneratorService;
import com.ssfMiniProject.BmiTrackerApp.services.UserRedisService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
public class GeneratorRestController {

    private static final Logger logger = LoggerFactory.getLogger(GeneratorRestController.class);

    @Autowired
    BmiGeneratorService service;

    @Autowired
    UserRedisService redisService;

    @Autowired
    @Qualifier ("userRedisConfig")
    RedisTemplate<String, User> redisTemplate;

    @GetMapping (path="/user/{username}", consumes =MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
     
        logger.info("get" + username);
            Optional<User> optUser = redisService.getByUsername(username);

            if(optUser.isEmpty()){
            JsonObject errJson = Json.createObjectBuilder()
            .add("error","User " + username + " not found")
            .build();
            return ResponseEntity.status(404).body(errJson.toString());
            
        }
        
        return ResponseEntity.ok(optUser.get());
    }

    @GetMapping (path="/userList", consumes =MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getList(){
        String userList = redisService.getList();
       
        return ResponseEntity.ok(userList);
    }
}
