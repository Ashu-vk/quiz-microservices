package com.gateway.ApiGateway.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayRequestLogger implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String auth = exchange.getRequest().getHeaders().getFirst("Authorization");

        System.err.println(">>>> Gateway incoming path: " + path);
//        System.err.println(">>>> Authorization header: " + auth);

        return chain.filter(exchange);
    }
}

