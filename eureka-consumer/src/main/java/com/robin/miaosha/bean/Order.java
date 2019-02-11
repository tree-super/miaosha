package com.robin.miaosha.bean;

import lombok.Data;
 
@Data
public class Order {
 
	private String id;			//主键ID
	private String name;		//姓名
	private String url;
	private String userAgent;
}

