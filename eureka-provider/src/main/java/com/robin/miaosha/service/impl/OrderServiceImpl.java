package com.robin.miaosha.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robin.miaosha.bean.Order;
import com.robin.miaosha.service.OrderService;
import com.robin.miaosha.util.RedisGlobalLock;

@Service
public class OrderServiceImpl implements OrderService {
	private Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private static final String PREFIX = RedisGlobalLock.class.getTypeName();
	
	@Autowired
	RedisGlobalLock redisGlobalLock;
	
	public Order miaosha(String id) {
		logger.error("miaosha failed order id:{}", id );
		String key = PREFIX + id;

		if(redisGlobalLock.tryLock(key, 30*1000L, TimeUnit.MILLISECONDS)){
//		    doSomething();
		    redisGlobalLock.unlock(key);
		}
		return null;
	}

}
