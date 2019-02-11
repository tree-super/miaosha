package com.robin.miaosha;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaProviderApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaProviderApp.class).profiles(args[0]).run(args);
	}

}
