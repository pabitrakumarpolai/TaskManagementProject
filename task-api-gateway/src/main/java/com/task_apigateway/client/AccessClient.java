package com.task_apigateway.client;

import com.task_apigateway.model.Access;
import com.task_apigateway.model.RoleName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "USER-SERVICE", path = "/api/access")
public interface AccessClient {

    @GetMapping
    List<Access> getAllAccessControls();

    @GetMapping("/role/{roleName}")
    List<Access> getAccessControlsByRole(@PathVariable("roleName") RoleName roleName);
}