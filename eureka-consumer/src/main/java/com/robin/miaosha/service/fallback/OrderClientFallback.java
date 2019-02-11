package com.robin.miaosha.service.fallback;

import com.robin.miaosha.bean.Order;
import com.robin.miaosha.service.OrderClient;

public class OrderClientFallback implements OrderClient {

	public Order miaosha(String id) {
		return null;
	}

}
