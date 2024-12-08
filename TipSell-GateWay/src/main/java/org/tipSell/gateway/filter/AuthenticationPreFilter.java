package org.tipSell.gateway.filter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.tipSell.gateway.clientConfig.WebClientConfig;

import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Mono;



@Component
public class AuthenticationPreFilter extends AbstractGatewayFilterFactory<AuthenticationPreFilter.Config> {

	public static class Config {}	
	
	@Autowired
	private WebClientConfig webClientConfig;
	
//	@Value("@{api.config}")
	private List<String> openApiEndpoints = Arrays.asList("/eureka","/protocol/openid-connect/**");

	
	private Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream().map(uri -> uri.replace("/**", "")).noneMatch(uri -> request.getURI().getPath().contains(uri));

	 @Override
	 public GatewayFilter apply(Config config) {
	        return (exchange, chain) -> {
	            ServerHttpRequest request = exchange.getRequest();
//	            log.info("**************************************************************************");
//	            log.info("URL is - " + request.getURI().getPath());
	            String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

	            HttpHeaders httpHeaders = new HttpHeaders();
	    		httpHeaders.add(HttpHeaders.AUTHORIZATION, bearerToken);
	    		
	            if(isSecured.test(request)) {
	           return  webClientConfig.tokenValidationAPIExchange(httpHeaders, exchange,chain);
	            }
	            return chain.filter(exchange);
	        };
	    }
}
