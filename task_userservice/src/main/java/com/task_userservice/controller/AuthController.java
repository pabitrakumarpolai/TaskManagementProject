package com.task_userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.task_userservice.config.jwtconfig.JwtService;
import com.task_userservice.exception.CustomException;
import com.task_userservice.payload.JwtResponse;
import com.task_userservice.payload.Login;
import com.task_userservice.payload.SignupRequest;
import com.task_userservice.service.UserService;
import com.task_userservice.service.impl.UserDetailsServiceImp;
import io.jsonwebtoken.Jwt;
import jakarta.validation.Valid;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JwtService jwtService;
    private UserDetailsServiceImp userDetailsServiceImp;
    private PasswordEncoder encoder;
    private UserService userService;
    private AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService, UserDetailsServiceImp userDetailsServiceImp,
                          PasswordEncoder encoder, UserService userService,
                          AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.encoder = encoder;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticateAndGetToken(@RequestBody Login loginForm) {
        logger.info("Login request {}", loginForm);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.getUserName(), loginForm.getPassword()
        ));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity registerUser(@RequestBody String object) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (object == null) {
                throw new CustomException("user can't be empty");
            }
            userService.registerUser(object, result);
            if (result.containsKey("error")) {
                return new ResponseEntity<>(result.get("error"), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (CustomException | JSONException | JsonProcessingException ex) {
            result.put("error", ex.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/validate/{token}")
    public ResponseEntity validateJwtToken(@PathVariable String token) {
        Map<String, Object> result = new HashMap<>();
        if (token.isEmpty()) {
            throw new CustomException("Token can't Be Empty");
        }
        jwtService.isTokenValid(token);
        result.put("Success", "Token is valid");
        return ResponseEntity.ok(result);
    }


}

