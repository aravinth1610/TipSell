package org.tipSell.gateway.filter;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
@Order(-2) // Ensure it runs before the default error handler
public class ErrorWebHandler implements ErrorWebExceptionHandler {

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		//need to keep logger
		
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
        String dateTime = formatter.format(new Date());

		
	  HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR; // Default to 500
      String errorMessage = "{\"status\": \"Failure\",\"message\": \"Internal Server Error.\",\"data\": [{\"errorCode\": \"ABI002\",\"reason\": \"Internal Server\",\"schemaPath\": "+exchange.getRequest().getURI().getPath()+",\"timestamp\": "+dateTime+"}]}";


		if (ex instanceof org.springframework.web.server.ResponseStatusException) {
			status = ((org.springframework.web.server.ResponseStatusException) ex).getStatusCode();

			if (status == HttpStatus.NOT_FOUND) {
				 errorMessage = String.format("{\"status\": \"Failure\",\"message\": \"Resource not found.\",\"data\": [{\"errorCode\": \"ABI002\",\"reason\": \"Not Found\",\"schemaPath\": \"%s\",\"timestamp\": %s}]}",exchange.getRequest().getURI().getPath(),dateTime);

			} else if (status == HttpStatus.SERVICE_UNAVAILABLE) {
				 errorMessage = String.format("{\"status\": \"Failure\",\"message\": \"Services will be Up or Check the Base Path.\",\"data\": [{\"errorCode\": \"ABI002\",\"reason\": \"SERVICE_UNAVAILABLE\",\"schemaPath\": \"%s\",\"timestamp\": %s}]}",exchange.getRequest().getURI().getPath(),dateTime);
			}
		}

		exchange.getResponse().setStatusCode(status);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		return exchange.getResponse().writeWith(
				Mono.just(exchange.getResponse().bufferFactory().wrap(errorMessage.getBytes(StandardCharsets.UTF_8))));
	}

}
