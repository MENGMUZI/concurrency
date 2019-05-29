package com.mmall.concurrency.example.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author : mengmuzi
 * create at:  2019-05-29  10:32
 * @description: redis的配置
 */
@Configuration
public class RedisConfig {
    @Bean(name = "redisPool")
    public JedisPool jedisPool(@Value("${jedis.host}") String host,
                               @Value("${jedis.port}") int port){
        return new JedisPool(host, port);
    }
}
