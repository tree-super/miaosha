package com.robin.eureka.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {
//	private static final String SERVER_NAME = "server.name";
//	private static final String SERVER_NAMES[] = {"vs1","vs2"};

	public static void main(String[] args) {
//		-Dserver.name=eureka-vs1
//		-Dserver.name=eureka-vs2
//		ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
//		String profiles =applicationArguments.getOptionValues(SERVER_NAME).get(0);
		new SpringApplicationBuilder(EurekaApp.class).profiles(args[0]).run(args);
	}

}
