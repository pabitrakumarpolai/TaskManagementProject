package com.task_apigateway.filter;

import com.task_apigateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {


    private RouteValidator validator;


    private JwtUtil jwtUtil;


    private RoleValidator roleValidator; // New class to handle role-based access control

    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil, RoleValidator roleValidator) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
        this.roleValidator = roleValidator;
    }

    public AuthenticationFilter() {
        super(Config.class);
    }

    /**
     *
     * @param config
     * @return
     */

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            if (validator != null && validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return Mono.error(new AccessDeniedException("Missing authorization header"));
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {

                    if (!jwtUtil.isTokenValid(authHeader)) {
                        return Mono.error(new AccessDeniedException("Invalid JWT token"));
                    }

                    List<String> roles = jwtUtil.extractRoles(authHeader);

                    if (!roleValidator.hasAccess(exchange.getRequest().getPath().value(), roles)) {
                        return Mono.error(new AccessDeniedException("Unauthorized access"));
                    }

                } catch (Exception e) {
                    return Mono.error(new AccessDeniedException("Unauthorized access to application"));
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
        // Configuration properties, if any, can be added here
    }
}