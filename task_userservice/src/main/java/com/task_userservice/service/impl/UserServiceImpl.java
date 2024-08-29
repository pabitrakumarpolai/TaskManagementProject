package com.task_userservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_userservice.entity.Enum.RoleName;
import com.task_userservice.entity.Role;
import com.task_userservice.entity.User;
import com.task_userservice.exception.CustomException;
import com.task_userservice.exception.ResourceNotFoundException;
import com.task_userservice.payload.SignupRequest;
import com.task_userservice.repository.RoleRepository;
import com.task_userservice.repository.UserRepository;
import com.task_userservice.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void registerUser(String object, Map<String, Object> result) throws JSONException, JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONObject jsonObject = new JSONObject(object);
        SignupRequest signUpRequest = objectMapper.readValue(object, SignupRequest.class);
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            throw new CustomException("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException("Error: Email is already in use!");
        }
        // Create new user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUserName(signUpRequest.getUserName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        //set role for new user
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new CustomException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new CustomException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "ROLE_MANAGER":
                        Role modRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                                .orElseThrow(() -> new CustomException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new CustomException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        User saveUser = userRepository.save(user);
        result.put("success", "User registered successfully");
        result.put("userId", saveUser.getId());
    }


    @Override
    public User getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        return user;
    }

    @Override
    public void assignRoleToUser(String userId, String roleName) {

    }

   /* @Override
    public void registerUser(String object, Map<String, Object> result) throws JSONException, JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONObject jsonObject = new JSONObject(object);
        User user = objectMapper.readValue(object, User.class);
        userRepository.save(user);
        result.put("success", "User registered successfully");
        result.put("userId", user.getId());

    }*/

    @Override
    public List<User> getUsernamesByPrefix(String prefix) {
//        return userRepository.findByUsernamePrefix(prefix);
        return null;
    }

    @Override
    public void searchUsers(String keyWord, Map<String, Object> result) {
        if (keyWord == null && keyWord.isEmpty()) {
            throw new CustomException("This fild cant be empty");
        }
        List<User> users = userRepository.findByFirstNameContainingOrLastNameContainingOrUserName(keyWord, keyWord, keyWord);
//        List<User> users = userRepository.searchByKeyword(keyWord);
        if (users.isEmpty()) {
            result.put("message", "No User Found");
        }
        result.put("users", users);


    }
}
