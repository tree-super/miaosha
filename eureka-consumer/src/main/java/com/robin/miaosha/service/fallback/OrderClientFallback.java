package com.robin.miaosha.service.fallback;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.robin.miaosha.bean.MiaoshaOrder;
import com.robin.miaosha.bean.SimpleResult;
import com.robin.miaosha.common.Const;
import com.robin.miaosha.service.OrderClient;

@Component
public class OrderClientFallback implements OrderClient {
	private Logger logger= LoggerFactory.getLogger(OrderClientFallback.class);
	
	public SimpleResult miaosha(MiaoshaOrder miaoshaOrder) {
		String message = String.format("service fault !!!!!!!!!!!!!  miaosha failed order gid:%s==uid:%s", miaoshaOrder.getGid(), miaoshaOrder.getUid());
		logger.error(message);
		return new SimpleResult(SimpleResult.STATUS_FAIL, message);
	}
	
	public MiaoshaOrder genMiaosha() {
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setGid(Const.GOOD_NO_PREFIX);
		miaoshaOrder.setUid(Const.USER_NO_PREFIX);
		return miaoshaOrder;
	}

	public Map<String, String> see(String pv) {
		return null;
	}

}
