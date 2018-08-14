package com.gaoan.forever.config;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.gaoan.forever.config.redis.RedisClient;
import com.gaoan.forever.config.redis.RedisConfigBean;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by dotnar on 2017/5/17.
 */
@Configuration
@EnableConfigurationProperties(RedisConfigBean.class) // 开启属性注入,通过@autowired注入
@ConditionalOnClass(RedisClient.class) // 判断这个类是否在classpath中存在
public class RedisMessageConfig {

    @Autowired
    RedisConfigBean redisConfig;

    /**
     * 创建redis连接工厂
     * 
     * @return
     */
    @Bean
    public JedisConnectionFactory connectionFactory() {
	JedisConnectionFactory connection = new JedisConnectionFactory();
	BeanUtils.copyProperties(redisConfig, connection);
	return connection;
    }

    /**
     * 创建redis 操作模板
     * 
     * @param connectionFactory
     * @return
     */
    @Bean
    public StringRedisTemplate template(JedisConnectionFactory connectionFactory) {
	return new StringRedisTemplate(connectionFactory);
    }

    @Bean(name = "jedisPool")
    public JedisPool jedisPool() {
	JedisPoolConfig config = redisConfig.getPoolConfig();
	return new JedisPool(config, redisConfig.getHostName(), redisConfig.getPort());
    }

    @Bean
    @ConditionalOnMissingBean(RedisClient.class) // 容器中如果没有RedisClient这个类,那么自动配置这个RedisClient
    public RedisClient redisClient(@Qualifier("jedisPool") JedisPool pool) {
	RedisClient redisClient = new RedisClient();
	redisClient.setJedisPool(pool);
	return redisClient;
    }
}
