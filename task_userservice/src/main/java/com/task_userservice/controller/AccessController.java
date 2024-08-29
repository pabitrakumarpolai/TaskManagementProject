package com.task_userservice.controller;

import com.task_userservice.entity.Enum.RoleName;
import com.task_userservice.entity.Access;
import com.task_userservice.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    @Autowired
    private AccessRepository accessRepository;

    @GetMapping
    public List<Access> getAllAccessControls() {
        return accessRepository.findAll();
    }

    @GetMapping("/role/{roleName}")
    public List<Access> getAccessControlsByRole(@PathVariable RoleName roleName) {
        return accessRepository.findByRoles_Name(roleName);
    }
}