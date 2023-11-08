package com.java3y.austin.service.api.impl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisTest {
    @Autowired
    private static StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        sendRedisShuai();
        System.out.println(redisTemplate.opsForValue().get("shuai22"));
    }

    private static void sendRedisShuai() {
        for(int i=0;i<100;i++){
            redisTemplate.opsForValue().set("shuai"+String.valueOf(i),"chenxu"+String.valueOf(i),1, TimeUnit.HOURS);
        }
    }

}
