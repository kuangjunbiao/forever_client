package com.gaoan.forever.utils.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Ehcache 缓存工具类
 *
 * @author longshengtang
 */
@Component
public class CacheUtils {
    private static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    public CacheUtils() {
	logger.info("开始初始化CacheUtils---------------------------");
    }

    /**
     * 将对应的键值对添加到缓存中，并设定超时时间
     *
     * @param key
     *            缓存key
     * @param value
     *            缓存value
     * @param timeout
     *            超时时间 -1 不超时
     * @param unit
     *            超时时间的单位
     */
    public static void put(Object key, Object value, long timeout, TimeUnit unit) {
	try {
	    long s = System.nanoTime();
	    if (timeout == -1) {
		valOps.set(key, value);
	    } else {
		valOps.set(key, value, timeout, unit);
	    }
	    long end = System.nanoTime();
	    logger.info("CacheUtils.put-key=" + key + ",obj===============================================" + value
		    + "，耗时=" + CommStringUtils.getFmt4Num((end - s))
		    + (timeout == -1 ? "不超时" : ",timeout=" + CommStringUtils.getFmt4Num(timeout) + ",unit=" + unit));
	} catch (Exception e) {
	    e.printStackTrace();
	    logger.info(
		    "CacheUtils.put-key=" + key + ",obj=" + value + "，异常=" + CommStringUtils.getStackTraceAsString(e));
	}
    }

    /**
     * 将对应的键值对添加到缓存中，并设定超时时间（单位为秒）
     *
     * @param key
     *            缓存key
     * @param value
     *            缓存value
     * @param timeout
     *            超时时间（单位为秒）-1 不超时
     */
    public static void put(Object key, Object value, long timeout) {
	put(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获得set 缓存
     * 
     * @param key
     * @return
     */
    public static Object get(Object key) {

	Object cacheValObj = valOps.get(key);
	return cacheValObj;
    }

    /**
     * 将对应的键值对添加到缓存中，并设定超时时间为3分钟
     *
     * @param key
     *            缓存key
     * @param value
     *            缓存value
     */
    public static void put(Object key, Object value) {
	put(key, value, 1000 * 60 * 3);
    }

    public static boolean validate(String cacheKey, String cacheValue) {
	boolean ref = false;
	Object cacheValObj = valOps.get(cacheKey);
	String sourceCode = "";
	if (cacheValObj != null) {
	    sourceCode = cacheValObj.toString();
	    ref = StringUtils.equalsIgnoreCase(cacheValue, sourceCode);
	}
	return ref;
    }

    public static boolean validateAndRemove(String cacheKey, String cacheValue) {
	try {
	    long s = System.nanoTime();
	    Object cacheValObj = valOps.get(cacheKey);
	    long e = System.nanoTime();
	    logger.info("validateAndRemove从缓存中获取cacheKey=" + cacheKey + "耗时：" + CommStringUtils.getFmt4Num((e - s)));
	    if (cacheValObj == null) {
		logger.info("validateAndRemove没有找到对应cacheKey=" + cacheKey);
		return false;
	    }

	    if (StringUtils.equalsIgnoreCase(cacheValObj.toString(), cacheValue)) {
		logger.info("validateAndRemove.cacheKey=" + cacheKey + " and cacheValue=" + cacheValue + " 验证成功！");
		executorService.execute(() -> {
		    redisTemplate.delete(cacheKey);// 异步删除缓存信息
		});
		return true;
	    }
	} catch (Exception e1) {
	    logger.error("validateAndRemove异常：" + CommStringUtils.getStackTraceAsString(e1));
	}

	return false;
    }

    /*************************************************** begin **************************************************************/

    /**
     * 计数
     * 
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public static Long increment(Object key, Object hashKey, long delta) {
	return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 计数
     * 
     * @param key
     * @param delta
     */
    public static Long increment(Object key, long delta) {
	return valOps.increment(key, delta);
    }

    /**
     * 将对应的键值对添加到缓存中
     * 
     * @param key
     * @param value
     * @param isOutTime
     *            true:默认设定3分钟超时时间 ,false:不超时
     */
    public static void put(Object key, Object value, boolean isOutTime) {
	put(key, value, isOutTime ? 1000 * 60 * 3 : -1);
    }

    /**
     * 获得redis数据库所有的key
     * 
     * @param pattern
     * @return
     */
    public static Set<Object> keys(String pattern) {
	return redisTemplate.keys(pattern);
    }

    /**
     * 判断redis数据库是否有对应的key
     * 
     * @param key
     * @return
     */
    public static boolean exist(String key) {
	return redisTemplate.execute(new RedisCallback<Boolean>() {
	    @Override
	    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
		return connection.exists(key.getBytes());
	    }
	});
    }

    /**
     * 删除所有指定数据库的数据
     */
    public static long flushDB() {
	return redisTemplate.execute(new RedisCallback<Long>() {
	    @Override
	    public Long doInRedis(RedisConnection connection) throws DataAccessException {
		connection.flushDb();
		return 1L;
	    }
	});
    }

    @SuppressWarnings("unchecked")
    public static void del(String... keys) {
	if (keys != null && keys.length > 0) {
	    if (keys.length == 1) {
		redisTemplate.delete(keys[0]);
	    } else {
		redisTemplate.delete(CollectionUtils.arrayToList(keys));
	    }
	}
    }

    /**
     * 删除
     * 
     * @param key
     */
    public static void del(String key) {
	redisTemplate.delete(key);
    }

    /**
     * 批量模糊删除 （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     * 
     * @param pattern
     */
    public static void batchDel(String... pattern) {
	for (String kp : pattern) {
	    redisTemplate.delete(redisTemplate.keys(kp + "*"));
	}
    }

    /**
     * 添加 key field 缓存
     * 
     * @param key
     * @param field
     * @param obj
     */
    public static void putHashOps(Object key, Object field, Object obj) {
	redisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * 判断hashkey是否存在
     * 
     * @param key
     * @param field
     * @return
     */
    public static boolean hasKey(Object key, Object field) {
	return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 获得 key field 缓存
     * 
     * @param key
     * @param field
     * @return
     */
    public static Object getHashOpsByField(String key, String field) {
	return redisTemplate.boundHashOps(key).get(field);
    }

    public static Object getHashOpsByField(Object key, Object field) {
	return redisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 获得key HashOps 缓存
     * 
     * @param key
     * @return
     */
    public static Object getHashOps(String key) {
	BoundHashOperations<Object, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
	Map<Object, Object> map = boundHashOperations.entries();
	return map;
    }

    /**
     * 删除 HashOps 缓存
     * 
     * @param key
     * @param fields
     */
    public static void delHashOpsByField(String key, String... fields) {
	BoundHashOperations<Object, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
	for (String field : fields) {
	    boundHashOperations.delete(field);
	}
    }

    public static void delHashOpsByField(Object key, Object... fields) {
	BoundHashOperations<Object, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
	for (Object field : fields) {
	    boundHashOperations.delete(field);
	}
    }

    /**
     * 
     * @param key
     * @param value
     * @param score
     */
    public static void zadd(Object key, Object value, double score) {
	redisTemplate.boundZSetOps(key).add(value, score);
    }

    public static Long zcard(Object key) {
	return redisTemplate.opsForZSet().zCard(key);
    }

    public static Set<TypedTuple<Object>> rangeWithScores(Object key, long start, long end) {
	return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    public static Set<TypedTuple<Object>> reverseRangeWithScores(Object key, long start, long end) {
	return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    public static Set<Object> getZSetByrangeByScore(String key, double min, double max, long offset, long count) {
	return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    public static Set<Object> getZSetByrangeByScore(String key, double min, double max) {
	return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public static void removeZSet(Object key, Object... values) {
	redisTemplate.opsForZSet().remove(key, values);
    }

    public static Long lpush(Object key, Object value) {
	return redisTemplate.opsForList().leftPush(key, value);
    }

    public static Long rpush(Object key, Object value) {
	return redisTemplate.opsForList().rightPush(key, value);
    }

    public static Long lRem(Object key, long i, Object value) {
	return redisTemplate.opsForList().remove(key, i, value);
    }

    public static List<Object> getListByRange(Object key, long start, long end) {
	return redisTemplate.opsForList().range(key, start, end);
    }

    public static Long getListSize(Object key) {
	return redisTemplate.opsForList().size(key);
    }

    /*************************************************** begin **************************************************************/

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
	CacheUtils.redisTemplate = redisTemplate;
    }

    @Resource(name = "redisTemplate")
    public void setValOps(ValueOperations<Object, Object> valOps) {
	CacheUtils.valOps = valOps;
    }

    private static RedisTemplate<Object, Object> redisTemplate;

    // @Resource(name = "redisTemplate")
    private static ValueOperations<Object, Object> valOps;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**********************************************************************************************************/

    private static StringRedisTemplate stringredisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate redisTemplate) {
	CacheUtils.stringredisTemplate = redisTemplate;
    }

    public static List<Object> getHashOpsByFielString(String key, Collection<Object> fieldList) {
	return stringredisTemplate.opsForHash().multiGet(key, fieldList);
    }

    public static Object getHashOpsByFielString(String key, String field) {
	return stringredisTemplate.opsForHash().get(key, field);
    }

    public static Map<Object, Object> getStringHashOps(String key) {
	return stringredisTemplate.opsForHash().entries(key);
    }

    /**
     * 计数
     * 
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    public static Long incrementByString(String key, String hashKey, long delta) {
	return stringredisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public static Long incrementByString(String key, long delta) {
	return stringredisTemplate.opsForValue().increment(key, delta);
    }

    public static Set<Object> getHKeysString(String key) {
	return stringredisTemplate.opsForHash().keys(key);
    }

    public static void delString(String key) {
	stringredisTemplate.delete(key);
    }
}
