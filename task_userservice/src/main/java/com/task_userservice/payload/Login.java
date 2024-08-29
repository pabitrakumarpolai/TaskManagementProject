package com.task_userservice.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "Login{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
