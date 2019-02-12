package com.robin.miaosha.bean;

import lombok.Data;

@Data
public class SimpleResult {
	public static final String STATUS_SUCCESS = "1";
	public static final String STATUS_FAIL = "0";

	private String status;
	private String message;

	public SimpleResult() {
	}

	public SimpleResult(String status, String message) {
		this.status = status;
		this.message = message;
	}
}
