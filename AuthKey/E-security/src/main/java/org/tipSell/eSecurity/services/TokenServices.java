package org.tipSell.eSecurity.services;

import java.util.Map;
import java.util.Set;

import org.tipSell.eSecurity.payload.response.TokenResponse;

public interface TokenServices {

	String generateTokenSecureKey();

//	Map<String, String> authenticationCodeTokenGenerate(String clientId, String code);

	TokenResponse tokenGenerate(String realm, String code,String cisiKey ,String clientId, String clientSecret,String grantType,String userName, String password);

}
