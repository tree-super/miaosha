package com.robin.miaosha.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.robin.miaosha.bean.Order;
 
@RestController
public class OrderProviderController {
	@Autowired
	RedisTemplate<String, String> redisTemplate;
 
	@RequestMapping(value = "/miaosha/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Order miaosha(HttpServletRequest request, @PathVariable Long id){
		Order order = new Order();
		order.setId(id);
		order.setName("interview");
		order.setUrl(request.getRequestURL().toString());
		order.setUserAgent(request.getHeader("User-Agent"));
		return order;
	}
	
}
