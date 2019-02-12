package com.robin.miaosha.service;

import com.robin.miaosha.bean.MiaoshaOrder;
import com.robin.miaosha.bean.SimpleResult;
 
public interface OrderService {
 
	SimpleResult miaosha(MiaoshaOrder miaoshaOrder);
	/**
	 * 初始化1000库存
	 * @return
	 */
	SimpleResult initStock();
	/**
	 * 增加100 库存
	 * @return
	 */
	SimpleResult addStock100();
	/**
	 * 查询剩余 库存
	 * @return
	 */
	SimpleResult stockLeft();
	/**
	 * 随机一个单子
	 * @return
	 */
	MiaoshaOrder gen();
	
	
	
}