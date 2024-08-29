package com.task_apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/register",
            "/api/auth/authenticate",
            "/api/auth/validate"
            /*"/api/access",
            "/api/access/role/**"*/
    );

   /* public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));*/
   public Predicate<ServerHttpRequest> isSecured =
           request -> openApiEndpoints
                   .stream()
                   .noneMatch(uri -> request.getURI().getPath().startsWith(uri));


}
