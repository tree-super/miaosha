package com.robin.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaConsumerApp {

	public static void main(String[] args) {
		SpringApplication.run(EurekaConsumerApp.class, args);
	}

}
