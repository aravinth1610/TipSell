package org.tipSell.eSecurity.domain.services;

import java.util.Optional;

import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.uniCore.payload.request.CustomerRegisterRequest;

public interface OauthClientDomain {
	
//	OauthRealm svgCreateRealm(String realm, Long adminId);

//	OauthClient svgOauthClient(String clientId, String clientSecret, String grantType,String realm);

//	boolean isExistsClientId(String clientId);
//	
//	boolean isExistsRealm(String realm);
	
	boolean isExistsRealmBaseOnCiOrSi(String realm, String clientId,String secret);
    
	boolean verifyMail(String realm, String clientId);
	
//	void customerRegistorBaseOnclientId(CustomerRegisterRequest userRegister,String clientId);
	
	OauthClient getClientIDDetails(String clientId);
	
//	Customer getCustomerByMail(String mail);
	
//	void updateAuthenticationCode(String authenticationCode,Long customerId, String clientId);
	
//	Customer getCustomerDetailsByCode(String code);
	
}
