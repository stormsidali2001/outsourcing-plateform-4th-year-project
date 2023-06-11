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
            "/workers",
            "/workers/workers-exist",
//            "/auth/registration/user/worker",
            "/workers/skills",
            "/workers/categories",
            "/workers/worker/**",
            "/auth/registration/user",
            "/auth/validate-email",
            "/auth/validate-token",
            "/auth/admin/statistics",
            "/eureka",
//            "/chat/discussion",
            "/workers/admin/workers",

            "/job-request/all"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(

                            uri -> {
                                System.out.println(request.getURI().getPath()+"validating uri : "+request.getPath()+ " ==  " + uri+" validation result :"+request.getURI().getPath().equals(uri));
//                                return request.getURI().getPath().contains(uri);
//                                return uri.contains(request.getURI().getPath());
                              return   request.getURI().getPath().equals(uri);

                            }


                    );
}