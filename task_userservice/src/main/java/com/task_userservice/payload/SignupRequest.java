package com.task_userservice.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "name fild is mandatory")
    private String name;
    @NotBlank
    @Size(min = 5, max = 20, message = "User name shoud atlest 3 leter ")
    private String userName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
