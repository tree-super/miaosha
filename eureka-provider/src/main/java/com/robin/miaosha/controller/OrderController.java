package com.robin.miaosha.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.robin.miaosha.bean.MiaoshaOrder;
import com.robin.miaosha.bean.SimpleResult;
import com.robin.miaosha.service.OrderService;
 
@RestController
public class OrderController {
	@Autowired
	OrderService orderService;
	
	/**
	 * 秒杀服务
	 * @param request
	 * @param miaoshaOrder
	 * @return
	 */
	@PostMapping(value = "/miaosha", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SimpleResult miaosha(HttpServletRequest request, @RequestBody MiaoshaOrder miaoshaOrder){
		return orderService.miaosha(miaoshaOrder);
	}
	
	/**
	 * 生产秒杀的单子
	 * @param request
	 * @param miaoshaOrder
	 * @return
	 */
	@PostMapping(value = "/gen", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public MiaoshaOrder gen(){
		return orderService.gen();
	}
	
	/**
	 * @param request
	 * @param pv
	 * @return
	 */
	@PostMapping(value = "/see/{pv}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> see(HttpServletRequest request, @PathVariable String pv){
		Map<String, String> result = new HashMap<String, String>();
		result.put("pv", pv);
		result.put("Url", request.getRequestURL().toString());
//		result.put("UserAgent", request.getHeader("User-Agent"));
		return result;
	}
	
}
