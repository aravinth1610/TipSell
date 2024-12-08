package org.tipSell.gateway.filter;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


@Configuration
public class RouteConfig {

	@Bean
	protected RouteLocator routes(RouteLocatorBuilder builder, AuthenticationPreFilter authFilter) {
		return builder.routes()
				.route("Auth-Key",
						r -> r.path("/tipSell/authKey/**")
								.filters(f ->
								//f.rewritePath("/authentication-service(?<segment>/?.*)", "$\\{segment}")
								f.filter(authFilter.apply(new AuthenticationPreFilter.Config())))
								.uri("lb://Auth-Key"))
				.route("Order-Services",
						r -> r.path("/order/**")
								.filters(f -> 
								        //f.rewritePath("/user-service(?<segment>/?.*)", "$\\{segment}")
										f.filter(authFilter.apply(new AuthenticationPreFilter.Config())))
								.uri("lb://Order-Services"))
				.route("TipSell-Registry",
						r -> r.path("/eureka/web")
						.filters(f ->  f.setPath("/"))
								.uri("http://localhost:9000"))
				.route("discovery-server-static",
						r -> r.path("/eureka/**")
								.uri("http://localhost:9000"))
//				  .route("fallbackRoute", 
//	                        r -> r.alwaysTrue()
//	                              .filters(f -> f.setResponseHeader("Content-Type", "application/json")
//	                                             .rewritePath("/.*", "/")
//	                                             .setStatus(HttpStatus.NOT_FOUND))
//	                              .uri("no://op")) // Use a non-routable URI
				.build();
	}
	
}
