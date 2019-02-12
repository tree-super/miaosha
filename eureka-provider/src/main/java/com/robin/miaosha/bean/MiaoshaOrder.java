package com.robin.miaosha.bean;

import lombok.Data;
 
@Data
public class MiaoshaOrder {
 
	private String gid;			//商品id
	private String uid;		//用户id
	public MiaoshaOrder() {
	}

	public MiaoshaOrder(String gid, String uid) {
		this.gid = gid;
		this.uid = uid;
	}
}

