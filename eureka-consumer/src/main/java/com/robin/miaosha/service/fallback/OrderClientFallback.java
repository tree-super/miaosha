package com.robin.miaosha.service.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.robin.miaosha.bean.Order;
import com.robin.miaosha.service.OrderClient;

@Component
public class OrderClientFallback implements OrderClient {
	private Logger logger= LoggerFactory.getLogger(OrderClientFallback.class);
	public Order miaosha(String id) {
		logger.error("miaosha failed order id:{}", id );
		return null;
	}

}
