package com.example.apigateway.filter;
import com.example.apigateway.dto.ValidateTokenResponse;
import com.example.apigateway.proxy.AuthProxy;
import com.example.apigateway.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
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
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7).trim();
                }
                try {
                    System.out.println("authenticating using token "+authHeader);
                    System.out.println("-------------------------------------------------------");
                    Mono<ValidateTokenResponse> resultMono = authProxy.validateToken(authHeader);
                    resultMono.subscribe(
                            (  result) -> {


                                // Handle the result object here
                                System.out.println("Received response: " + result.toString());
                                exchange.getRequest().mutate()
                                        .header("X-email", result.getEmail())
                                        .header("X-role", result.getRole())
                                        .header("X-userId",result.getUserId())
                                        .build();
                            },
                            error -> {
                                // Handle any errors that occur during the call
                                System.out.println("Error occurred: " + error.getMessage());
//                                throw new RuntimeException("un authorized access to application");
                            }
                    );

                } catch (Exception e) {
                    System.out.println("invalid access...! "+e.getMessage());
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
