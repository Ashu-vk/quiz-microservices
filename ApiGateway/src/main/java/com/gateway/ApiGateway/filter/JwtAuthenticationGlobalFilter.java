package com.gateway.ApiGateway.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGlobalFilter implements GlobalFilter, Ordered{

	@Override
	public int getOrder() {
		return -10;
	}

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh",
            "/actuator/health"
        );
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	    String path = exchange.getRequest().getPath().value();
        if (PUBLIC_PATHS.stream().anyMatch(path::contains)) {
            return chain.filter(exchange);
        }
		// TODO Auto-generated method stub
		return exchange.getPrincipal()
				.cast(JwtAuthenticationToken.class)
				.flatMap(auth-> {
					Jwt jwt = auth.getToken();
					ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
							    .header("X-Auth-User", jwt.getSubject())
//			                    .header("X-Auth-UserId", jwt.getClaim("userId").toString())
			                    .header("X-Auth-Roles", String.join(",", jwt.getClaimAsStringList("roles")))
			                    .build();
			                
			                return chain.filter(exchange.mutate().request(modifiedRequest).build());
				});
	}

}
