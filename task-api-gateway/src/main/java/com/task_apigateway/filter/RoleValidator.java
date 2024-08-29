package com.task_apigateway.filter;

import com.task_apigateway.client.AccessClient;
import com.task_apigateway.model.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.*;

@Service
public class RoleValidator {

    @Autowired
    private AccessClient accessClient;

    @Cacheable("accessControls")
    public List<Access> getAccessControls() {
        return accessClient.getAllAccessControls();
    }

    public boolean hasAccess(String path, List<String> userRoles) {
        List<Access> accesses = getAccessControls();

        for (Access access : accesses) {
            if (pathMatches(path, access.getPath())) {
                return userRoles.stream().anyMatch(role ->
                        access.getRoles().stream().anyMatch(accessRole ->
                                accessRole.getName().name().equals(role)
                        )
                );
            }
        }
        return false; // Default to deny if no matching role is found
    }

    private boolean pathMatches(String path, String pattern) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match(pattern, path);
    }
}