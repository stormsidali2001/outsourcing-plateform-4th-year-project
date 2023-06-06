package com.example.apigateway.filter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.function.Predicate;
import java.util.List;
@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/signin",
            "/auth/registeration/user/worker",
            "/auth/registration/user",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(

                            uri -> {
                                System.out.println("validating uri : "+request.getPath()+ " ==  " + uri+" validation result :"+request.getURI().getPath().contains(uri));
                                return request.getURI().getPath().contains(uri);
                            }

                    );

}