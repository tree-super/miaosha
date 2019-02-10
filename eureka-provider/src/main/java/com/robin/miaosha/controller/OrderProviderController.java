package com.robin.miaosha.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.robin.miaosha.bean.Order;
 
@RestController
public class OrderProviderController {
 
	@RequestMapping(value = "/miaosha/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Order miaosha(@PathVariable Long id){
		Order order = new Order();
		order.setId(id);
		order.setName("interview");
		return order;
	}
	
}
