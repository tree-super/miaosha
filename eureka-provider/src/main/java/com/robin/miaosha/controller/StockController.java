package com.robin.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.robin.miaosha.bean.SimpleResult;
import com.robin.miaosha.service.OrderService;
 
@RestController
@RequestMapping(value="/stock")
public class StockController {
	@Autowired
	OrderService orderService;
	
	/**
	 * 初始1000个库存
	 */
	@GetMapping(value = "/int", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SimpleResult init(){
		return orderService.initStock();
	}
	
	/**
	 * 增加100个库存
	 */
	@GetMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SimpleResult add(){
		return orderService.addStock100();
	}
	
	/**
	 * 剩余库存
	 */
	@GetMapping(value = "/left", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SimpleResult left(){
		return orderService.stockLeft();
	}
}
