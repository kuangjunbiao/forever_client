package com.gaoan.forever.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by dotnar on 2017/5/7.
 */
@ConfigurationProperties(locations = { "classpath:redis-config.yml" }, prefix = "spring.redis")
public class RedisConfigBean {

    private String hostName;
    private int port;
    private String password;
    private int database;
    private JedisPoolConfig poolConfig;
    private int timeout;

    public String getHostName() {
	return hostName;
    }

    public void setHostName(String hostName) {
	this.hostName = hostName;
    }

    public int getPort() {
	return port;
    }

    public void setPort(int port) {
	this.port = port;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public int getDatabase() {
	return database;
    }

    public void setDatabase(int database) {
	this.database = database;
    }

    public JedisPoolConfig getPoolConfig() {
	return poolConfig;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
	this.poolConfig = poolConfig;
    }

    public int getTimeout() {
	return timeout;
    }

    public void setTimeout(int timeout) {
	this.timeout = timeout;
    }

}
