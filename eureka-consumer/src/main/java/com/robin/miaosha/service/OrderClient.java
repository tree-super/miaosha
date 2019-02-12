package com.robin.miaosha.service;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.robin.miaosha.bean.MiaoshaOrder;
import com.robin.miaosha.bean.SimpleResult;
import com.robin.miaosha.service.fallback.OrderClientFallback;
 
@FeignClient(value="miaosha-provider", fallback=OrderClientFallback.class)
public interface OrderClient {
 
	/**
	 * 调用秒杀服务
	 * @return
	 */
	@PostMapping(value = "/see/{pv}", produces = MediaType.APPLICATION_JSON_VALUE)
	Map<String, String> see(@PathVariable("pv") String pv);
	
	/**
	 * 调用秒杀服务
	 * @return
	 */
	@PostMapping(value = "/miaosha", produces = MediaType.APPLICATION_JSON_VALUE)
	SimpleResult miaosha(@RequestBody MiaoshaOrder miaoshaOrder);
	
	/**
	 * 生成订单参数
	 * @return
	 */
	@PostMapping(value = "/gen", produces = MediaType.APPLICATION_JSON_VALUE)
	MiaoshaOrder genMiaosha();
	
}