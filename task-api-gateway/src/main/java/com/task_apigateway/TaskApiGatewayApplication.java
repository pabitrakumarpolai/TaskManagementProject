package com.task_apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApiGatewayApplication.class, args);
	}

}
