package com.hllolluni.api_gateway.authfilter;

import com.hllolluni.api_gateway.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (validator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header!");
                }
                try {
                    String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).substring(7);
                    jwtUtil.validateToken(token);
                    String email = this.jwtUtil.getUsernameFromToken(token);
                    String roles = ((ArrayList<String>)this.jwtUtil.getAllClaimsFromToken(token).get("roles")).get(0);
                    exchange.getRequest().mutate().header("email", email);
                    exchange.getRequest().mutate().header("authorities", roles);
                }catch (Exception e) {
                    log.error("Invalid access token!");
                    throw new RuntimeException("Invalid access token!");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
