package com.robin.miaosha.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.robin.miaosha.bean.Order;
import com.robin.miaosha.service.fallback.OrderClientFallback;
 
@FeignClient(value="miaosha-provider", fallback=OrderClientFallback.class)
public interface OrderClient {
 
	@RequestMapping(method = RequestMethod.POST, value = "/miaosha/{id}")
	Order miaosha(@PathVariable("id") String id);
	
}