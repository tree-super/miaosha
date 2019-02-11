package com.robin.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.robin.miaosha.bean.Order;
import com.robin.miaosha.service.OrderClient;
 
@Controller
@Configuration
public class OrderController {

	@Autowired
	OrderClient client;
	
	@GetMapping(value = "/router/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Order router(@PathVariable String id){
//		springcloud redis watch 分布式锁
		return client.miaosha(id);
	}
	
}
