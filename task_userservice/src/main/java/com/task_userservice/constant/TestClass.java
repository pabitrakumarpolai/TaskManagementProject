package com.task_userservice.constant;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestClass {
    public static void main(String[] args) {
        PasswordEncoder ps =new BCryptPasswordEncoder();
        System.out.println(ps.encode("test"));
    }
}
