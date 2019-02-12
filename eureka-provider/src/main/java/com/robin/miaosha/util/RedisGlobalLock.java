package com.robin.miaosha.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Robin 自己实现锁
 */
@Component
public class RedisGlobalLock {

	private final static long LOCK_TRY_INTERVAL = 30L;
	private final static long LOCK_EXPIRE = 10;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 释放锁
	 * 
	 * @param key
	 *            锁Key
	 */
	public void unlock(String key) {
		Long oldExpireTime = (Long) redisTemplate.opsForValue().get(key);
		if (null != oldExpireTime && oldExpireTime >= System.currentTimeMillis()) {
			// 大于过期时间，则删除key
			redisTemplate.delete(key);
		}
	}

	/**
	 * 获取锁
	 * 
	 * @param key
	 *            锁键值
	 * @param timeout
	 *            超时时间
	 * @param time
	 *            全局锁生命周期
	 * @param unit
	 *            时间单位
	 * @return 是否获取到锁
	 */
	private boolean getLock(String key, long timeout) {
		try {
			long startTimeMillis = System.currentTimeMillis();
			do {
				long lockTime = System.currentTimeMillis() + LOCK_EXPIRE;
				Boolean isOk = redisTemplate.opsForValue().setIfAbsent(key, lockTime);
				if (isOk) {
					// 获得锁
					redisTemplate.expire(key, LOCK_EXPIRE, TimeUnit.SECONDS);
					return true;
				}
				// 获取过期时间
				Long oldExpireTime = (Long) redisTemplate.opsForValue().get(key);
				if (null == oldExpireTime) {
					oldExpireTime = 0L;
				}
				if (oldExpireTime >= System.currentTimeMillis()) {
					// 不小于系统时间并且过了超时时间，则不获取锁
					if ((System.currentTimeMillis() - startTimeMillis) > timeout) {
						return false;
					}

					// 休眠
					Thread.sleep(LOCK_TRY_INTERVAL);
				}
				// 新的过期时间
				long newExpireTime = System.currentTimeMillis() + LOCK_EXPIRE;
				Long currentExpireTime = (Long) redisTemplate.opsForValue().getAndSet(key, newExpireTime);
				if (null == currentExpireTime) {
					currentExpireTime = 0L;
				}
				if (currentExpireTime.equals(oldExpireTime)) {
					// 获取到锁
					redisTemplate.expire(key, LOCK_EXPIRE, TimeUnit.SECONDS);
					return true;
				}
			} while (true);
		} catch (InterruptedException e) {
			return false;
		}
	}
	
	/**
	 * 获取锁
	 * 锁一条商品记录
	 * 
	 * 锁的value 是上锁时间
	 */
	public boolean lock(String key) {
		long lockTime = System.currentTimeMillis() + LOCK_EXPIRE;
//		boolean result = redisTemplate.opsForValue().setIfAbsent(key, lockTime);
//		if (result) {
//			redisTemplate.expire(key, LOCK_EXPIRE, TimeUnit.SECONDS);
//			return true;
//		}
//		return false;
		return setIfAbsent(key, lockTime, LOCK_EXPIRE);
	}
	
	/** 尝试替代 setIfAbsent */
	String newSetIfAbsentScriptStr = " if 1 == redis.call('setnx',KEYS[1],ARGV[1]) then" +
	        " redis.call('expire',KEYS[1],ARGV[2])" +
	        " return 1;" +
	        " else" +
	        " return 0;" +
	        " end;";

	public RedisScript<Boolean> newSetIfAbsentScript = new DefaultRedisScript<Boolean>(newSetIfAbsentScriptStr,Boolean.class );
	
	public boolean setIfAbsent(String key, Long value, Long seconds){
	    List<String> keys = new ArrayList<String>();
	    keys.add(key);
	    Long[] args = {value, seconds};//GenericToStringSerializer<Long>
	    return (Boolean) redisTemplate.execute(newSetIfAbsentScript, keys, args);
	}
}
