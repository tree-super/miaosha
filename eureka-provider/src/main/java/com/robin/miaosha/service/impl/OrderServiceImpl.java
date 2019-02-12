package com.robin.miaosha.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.robin.miaosha.bean.MiaoshaOrder;
import com.robin.miaosha.bean.SimpleResult;
import com.robin.miaosha.common.Const;
import com.robin.miaosha.service.OrderService;
import com.robin.miaosha.util.RedisGlobalLock;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Random random = new Random();
	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	RedisGlobalLock redisGlobalLock;

	public SimpleResult miaosha(MiaoshaOrder miaoshaOrder) {
		String key = miaoshaOrder.getGid();
		String message = String.format("gid:%s==uid:%s", miaoshaOrder.getGid(), miaoshaOrder.getUid());
		boolean lock = redisGlobalLock.lock(key);
		// 获取锁核心判断
		if (lock) {
			try {
				redisTemplate.opsForHash().delete(Const.STOCK_KEY, key);//删除库存
				redisTemplate.opsForList().rightPush(Const.MIAOSHA_ORDER_KEY, message);//秒杀的单子存入list 以判断是否有重复单
				message = "***********U got the luck. " + message;
			} finally {// 注销锁 等1秒
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					redisGlobalLock.unlock(key);
				}
			}
			return new SimpleResult(SimpleResult.STATUS_SUCCESS, message);
		} else {
			message = "failed,try next. " + message;
		}
		return new SimpleResult(SimpleResult.STATUS_FAIL, message);
	}

	@Override
	public SimpleResult initStock() {
		redisTemplate.delete(Const.STOCK_KEY);
		int maxGoodNo = 10;
		//秒杀限时10分钟
		redisTemplate.opsForValue().set(Const.STOCK_MAX_GOOD_NO_KEY, maxGoodNo, 10L, TimeUnit.MINUTES);

		Map<String, String> stock = new HashMap<String, String>();
		for (int i = 0; i < maxGoodNo; i++) {
			stock.put(Const.GOOD_NO_PREFIX + i, Const.GOOD_NO_PREFIX + i);
		}
		redisTemplate.opsForHash().putAll(Const.STOCK_KEY, stock);
		return new SimpleResult(SimpleResult.STATUS_SUCCESS,
				"stock-total," + redisTemplate.opsForHash().size(Const.STOCK_KEY));
	}

	@Override
	public SimpleResult addStock100() {
		int maxNo = 0;
		try {
			maxNo = Integer.parseInt(redisTemplate.opsForValue().get(Const.STOCK_MAX_GOOD_NO_KEY).toString());
		} catch (Exception e) {
			return new SimpleResult(SimpleResult.STATUS_FAIL, "activity OVER");
		}
		Map<String, String> stock = new HashMap<String, String>();
		int start = maxNo+100;
		for (; maxNo < start; maxNo++) {
			stock.put(Const.GOOD_NO_PREFIX + maxNo, Const.GOOD_NO_PREFIX + maxNo);
		}
		redisTemplate.opsForHash().putAll(Const.STOCK_KEY, stock);
		redisTemplate.opsForValue().set(Const.STOCK_MAX_GOOD_NO_KEY, start);
		return new SimpleResult(SimpleResult.STATUS_SUCCESS,
				"stock-total," + redisTemplate.opsForHash().size(Const.STOCK_KEY));
	}

	@Override
	public SimpleResult stockLeft() {
		return new SimpleResult(SimpleResult.STATUS_SUCCESS,
				"stock-total," + redisTemplate.opsForHash().size(Const.STOCK_KEY));
	}

	@Override
	public MiaoshaOrder gen() {
		long size = redisTemplate.opsForHash().size(Const.STOCK_KEY);
		if(size < 1L){
			return new MiaoshaOrder(Const.GOOD_NO_PREFIX, Const.USER_NO_PREFIX);
		}
		long no = random.nextInt((int)size);
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGid(Const.GOOD_NO_PREFIX + no);
		miaoshaOrder.setUid(Const.USER_NO_PREFIX + no);
		return miaoshaOrder;
	}
}
