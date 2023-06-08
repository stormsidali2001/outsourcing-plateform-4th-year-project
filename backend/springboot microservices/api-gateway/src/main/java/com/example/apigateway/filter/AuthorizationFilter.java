package com.example.apigateway.filter;
import com.example.apigateway.dto.ValidateTokenResponse;
import com.example.apigateway.proxy.AuthProxy;
import com.example.apigateway.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

@Component
public class AuthorizationFilter  extends AbstractGatewayFilterFactory<AuthorizationFilter.Config>{
    @Autowired
    private RouteValidator validator;


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired @Lazy
    private AuthProxy authProxy;
        @Autowired
    private RestTemplate template;


    public AuthorizationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            System.out.println("-------------------------------------------------------");
            String path = exchange.getRequest().getPath().toString();
            System.out.println("----------- path = "+exchange.getRequest().getPath());
            System.out.println("-------------------------------------------------------");
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    System.out.println("iam here >>>>>>>> " + authHeader);
                    authHeader = authHeader.substring(7).trim();
                }
                try {

                    Mono<ValidateTokenResponse> resultMono = authProxy.validateToken(authHeader);
                    return resultMono.flatMap(result -> {
                                System.out.println("---------------------------------------");
                                // Handle the result object here
                                System.out.println("Received response: " + result.toString());
                                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                        .path(path.substring(path.indexOf("/",1)))
                                        .header("X-email", result.getEmail())
                                        .header("X-role", result.getRole())
                                        .header("X-userId", result.getUserId())
                                        .build();
                                ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                                return chain.filter(modifiedExchange);
//                                return chain.filter(exchange.mutate().request(modifiedRequest).build());
                            }).onErrorResume(error -> {
                        // Handle any errors that occur during the call
                        System.out.println("Error occurred: " + error.getMessage());
                        throw new RuntimeException("unauthorized access to application");
                    });


                } catch (Exception e) {
                    System.out.println("invalid access...! "+e.getMessage());
                    throw new RuntimeException("un authorized access to application");
                }
            }
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .path(path.substring(path.indexOf("/",1)))

                    .build();
            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
            return chain.filter(modifiedExchange);
        });
    }

    public static class Config {

    }
}
