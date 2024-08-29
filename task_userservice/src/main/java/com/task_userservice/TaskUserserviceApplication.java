package com.task_userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaskUserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskUserserviceApplication.class, args);
	}


}
