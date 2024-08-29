package com.task_userservice.controller;

import com.task_userservice.entity.User;
import com.task_userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

   /* @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody String object) {
        Map<String, Object> result = new HashMap<>();
        try {
            userService.registerUser(object, result);
            if (result.containsKey("error")) {
                return new ResponseEntity<>(result.get("error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            result.put("error", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

    }*/

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@PathVariable long userId) {
        User userById = userService.getUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<String>> searchUsers(@RequestParam("prefix") String prefix) {
        List<User> users = userService.getUsernamesByPrefix(prefix);
        List<String> usernames = users.stream()
                .map(User::getUserName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usernames);
    }

}
