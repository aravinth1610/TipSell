package org.tipSell.securityFilters;

import static org.tipSell.authKey.Constant.AuthKeyConstant.CORS_ALLOW_Methods;
import static org.tipSell.authKey.Constant.AuthKeyConstant.CORS_ALLOW_HEADERS;
import static org.tipSell.authKey.Constant.AuthKeyConstant.CORS_ALLOW_EXPOSEDHEADERS;

import java.util.Arrays;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;


public class CorsConfigurationFilter implements CorsConfigurationSource{

	@Override
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList(CORS_ALLOW_Methods));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList(CORS_ALLOW_HEADERS));
		config.setExposedHeaders(Arrays.asList(CORS_ALLOW_EXPOSEDHEADERS));
		config.setMaxAge(3600L);
		return config;

	}

}