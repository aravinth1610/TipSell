package org.tipSell.authKey.Constant;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;

public interface AuthKeyConstant {
	
	long ACCESS_TOKEN_VALIDITY = 7;
	
	long REFRESH_TOKEN_VALIDITY = 21;
		
	int CODE_VALIDITY = 10;
	
	String CLAIM_EXPIRATION = "Exp";
	
	String CLAIM_SUB = "sub";
	
	String CLAIM_PROFILES = "profile";
	
	String CLAIM_GRANTYPE = "grantType";
	
	String CLIENT_USER = "CLIENT_USER";
	
	String AUTHKEY_USER = "AUTHKEY_USER";
	
	String TOKEN_ISSUER = "Auth_Key";

	String AUTHORITIES = "authorities";

	String PASSWORD = "password";

	String CLIENT_CREDENTIALS = "client_credential";

	String AUTHORIZATION_CODE = "authorization_code";

	String REFRESH_TOKEN = "refresh_token";

	String TOKEN_PREFIX = "Bearer";

	String TOKEN_HEADER = "Jwt-Token";

	String OPTIONS_HTTP_METHOD = "options";

	String CORS_ALLOW_Methods[] = { HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
			HttpMethod.DELETE.name() };

	String CORS_ALLOW_HEADERS[] = { "Origins", "Accept-Control-Allow-Origin", "Content-Type",
			"x-www-form-urlencoded;charset=UTF-8", "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
			"Access-Control-Request-Method", "Access-Control-Request-Headers", "Cookie" };

	String CORS_ALLOW_EXPOSEDHEADERS[] = { "Origins", "Accept-Control-Allow-Origin", "Content-Type", "Accept",
			"Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
			"Access-Control-Request-Headers", "Cookie" };
	
    String[] PUBLIC_URLS = { "/protocol/openid-connect/**", "/images/**", "/static/**" };

	List<String> SHOULDNOTFILTERVALIDATOR = Arrays.asList(PUBLIC_URLS);

}
