package com.robin.miaosha.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.robin.miaosha.bean.MiaoshaOrder;
import com.robin.miaosha.bean.SimpleResult;
import com.robin.miaosha.common.Const;
import com.robin.miaosha.service.OrderClient;
 
@Controller
public class GateController {

	@Autowired
	OrderClient client;
	
	@RequestMapping(value = "/ribbon/see/{pv}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> router(HttpServletRequest request, @PathVariable String pv){
		Map<String, String> result = client.see(pv);
		result.put("UserAgent", request.getHeader("User-Agent"));
		return result;
	}
	
	@RequestMapping(value = "/miaosha", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SimpleResult miaosha(@RequestBody(required=false) MiaoshaOrder miaoshaOrder){
		if(miaoshaOrder == null || StringUtils.isBlank(miaoshaOrder.getGid())){
			//如果客户端没有传入参数，在此处拼装
			miaoshaOrder = client.genMiaosha();
		}
		if(Const.GOOD_NO_PREFIX.equals(miaoshaOrder.getGid())
				|| Const.USER_NO_PREFIX.equals(miaoshaOrder.getUid())){
			return new SimpleResult(SimpleResult.STATUS_FAIL, "No any good, activity OVER. ");
		}
		return client.miaosha(miaoshaOrder);
	}
	
	@GetMapping(value = "/gen", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public MiaoshaOrder gen(){
		return client.genMiaosha();
	}
	
}
