package com.task_userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.task_userservice.entity.User;
import com.task_userservice.payload.SignupRequest;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public interface UserService {


    // Method for authenticating a user
    /*AuthenticationResponse loginUser(LoginRequest request);*/

    // Method to retrieve user information by ID
    User getUserById(long userId);

    // Method to update user information by ID
//    User updateUserById(String userId, UserUpdateRequest request);

    // Method to assign a role to a user
    void assignRoleToUser(String userId, String roleName);

    void registerUser(String object, Map<String, Object> result) throws JSONException, JsonProcessingException;

    List<User> getUsernamesByPrefix(String prefix);

    void searchUsers(String keyWord ,Map<String ,Object> result);
}
