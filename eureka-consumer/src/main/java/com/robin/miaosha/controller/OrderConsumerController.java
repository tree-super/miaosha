package com.robin.miaosha.controller;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.robin.miaosha.bean.Order;
 
@Controller
@Configuration
public class OrderConsumerController {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@GetMapping(value = "/router")
	@ResponseBody
	public Order router(){
		RestTemplate template = getRestTemplate();
//		https://blog.csdn.net/mrspirit/article/details/80019617
//		springcloud redis watch 分布式锁
		return template.postForObject("http://eureka-provider/miaosha/1", null, Order.class);
	}
	
}
