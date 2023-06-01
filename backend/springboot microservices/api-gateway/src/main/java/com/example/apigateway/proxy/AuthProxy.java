package com.example.apigateway.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;
@Component
public class AuthProxy {
    private WebClient webClient;

    @Autowired
    public AuthProxy(@LoadBalanced WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<Object> validateToken(String token) {
        return webClient.get()
                .uri("http://auth-microservice/validate-token?token={token}", token)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .bodyToMono(Object.class);
    }
}