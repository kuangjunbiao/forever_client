package com.gaoan.forever.config;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import com.gaoan.forever.config.redis.RedisConfigBean;

//这个类用配置redis服务器的连接
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800, redisNamespace = "dotnar.trade.api.server")
@EnableConfigurationProperties(RedisConfigBean.class) // 开启属性注入,通过@autowired注入
public class RedisSessionConfig {

    @Autowired
    RedisConfigBean redisConfig;

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
	return new CookieHttpSessionStrategy();
    }

    @Bean
    public JedisConnectionFactory connectionFactory() {
	JedisConnectionFactory connection = new JedisConnectionFactory();
	BeanUtils.copyProperties(redisConfig, connection);
	return connection;
    }

    @Bean(name = "redisTemplate")
    RedisTemplate redisTemplate(JedisConnectionFactory connectionFactory) {
	RedisTemplate redisTemplate = new RedisTemplate<>();
	redisTemplate.setConnectionFactory(connectionFactory);

	return redisTemplate;
    }
}
