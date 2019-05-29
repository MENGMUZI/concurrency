package com.mmall.concurrency.example.cache;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author : mengmuzi
 * create at:  2019-05-29  10:39
 * @description: redis的客户端
 */
@Component
public class RedisClient {
    @Resource(name = "redisPool")
    private JedisPool jedisPool;

    public void set(String key , String value) throws Exception{
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }

    }

    public String get(String key) throws Exception{
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }


}
