package org.tipSell.eSecurity.domain.services.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.domain.entity.OauthToken;
import org.tipSell.eSecurity.domain.repository.UserRepository;
import org.tipSell.eSecurity.domain.repository.OauthClientRepository;
import org.tipSell.eSecurity.domain.repository.OauthRealmRepository;
import org.tipSell.eSecurity.domain.repository.OauthTokenRepository;
import org.tipSell.eSecurity.domain.services.OauthClientDomain;
import org.tipSell.uniCore.payload.request.CustomerRegisterRequest;

import lombok.AllArgsConstructor;

/**
 * @Author aravinth
 * @since 2024
 *
 *        This is a Class it is used to Save the Data By Reading the JSON File
 *        and It implements AuthKeyUserDomain
 */

@Service
@AllArgsConstructor
public class OauthClientDomainImpl implements OauthClientDomain {

	private final OauthClientRepository oathKeyClientRepo;
	private final OauthRealmRepository oAuthRealmRepo;
	private final OauthTokenRepository oAuthKeyTokenRepo;
	private final UserRepository customerRepo;

//	@Override
//	public OauthRealm svgCreateRealm(String realm, Long adminId) {
//
//		OauthRealm realmSvg = new OauthRealm();
//		realmSvg.setRealm(realm);
//		realmSvg = oAuthRealmRepo.save(realmSvg);
//		return realmSvg; // This if for only realm Name
////			svgOauthClient(clientId,clientSecret, adminId, grantType,realmSvg.getRealmUid());
//	}

//	@Override
//	public OauthClient svgOauthClient(String clientId, String clientSecret, String grantType,String realm) {


//		GrantTypes.fromGrantType(grantType);
//		System.out.println("After Grant Type");

//		OauthRealm getRealm = oAuthRealmRepo.findByRealm(realm);
		

//		oAuthRealmRepo.findRealmUidByRealm(realm);
		
//		OauthClient client = new OauthClient();
//		OauthToken oAuthKeyToken = new OauthToken();
//		client.setClientID(clientId);
//		client.setGrantType(GrantTypes.fromGrantType(grantType));
//		//client.setCreatedOn(new Date());
//		client.setCreatedIn(adminId);
//		client.setClientSecret(clientSecret);
//		client.setRealm(getRealm);
//		oathKeyClientRepo.save(client);
//
//		oAuthKeyToken.setGeneratedTokenExp(new Date(System.currentTimeMillis() + 86400 * 1000));
//		oAuthKeyToken.setRefressTokenExp(new Date(System.currentTimeMillis() + 86400 * 1000));
//		oAuthKeyToken.setClient(client);
//		oAuthKeyTokenRepo.save(oAuthKeyToken);
//		return null; // this is only for clientUid

//	}
//
//	@Override
//	public void customerRegistorBaseOnclientId(CustomerRegisterRequest userRegister,String clientId) {
//		System.out.println("***********"+clientId);
//		OauthClient client = oathKeyClientRepo.findByClientID(clientId);
//		Customer customer = new Customer();
//	
//	    Roles  role = new Roles();
//		role.setRoles(RoleTypes.USER);
//	    Set<Roles> setRole = new HashSet<>();
//	    setRole.add(role);
//	 	
//	    BeanUtils.copyProperties(userRegister, customer);
//		customer.setClient(client);
//		customer.setRoles(setRole);
//		customerRepo.save(customer);
//		roleRepo.save(role);
//		
//	}
	
//	public Customer getCustomerByMail(String mail) {
//		return customerRepo.findByGmail(mail);
//	}
	
	@Override
	public OauthClient getClientIDDetails(String clientId) {
		try {
		return oathKeyClientRepo.findByClientID(clientId);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
//	@Override
//	public Customer getCustomerDetailsByCode(String code) {
//		try {
//		return customerRepo.findByAuthenticationCode(code);
//		}catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException();
//		}
//	}

//	@Override
//	@Transactional(readOnly = true)
//	public boolean isExistsClientId(String clientId) {
//		return oathKeyClientRepo.existsByClientID(clientId);
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public boolean isExistsRealm(String realm) {
//		return oAuthRealmRepo.existsByRealm(realm);
//	}

	@Override
	@Transactional(readOnly = true)
	public boolean isExistsRealmBaseOnCiOrSi(String realm, String clientId,String secret) 
	{
		Integer isExists;
		if(secret == null || secret.isEmpty()) 
		{
			isExists = null;//oathKeyClientRepo.existsByRealmAndClientId(clientId, realm);
			
		}else 
		{
			isExists = oathKeyClientRepo.existsByRealmAndClientIdAndSecret(clientId,secret,realm);
		}			
		System.out.println(isExists);
		return (isExists > 0) ? true : false;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean verifyMail(String realm, String clientId) {
		Integer isVerified = oathKeyClientRepo.verifyMail(clientId, realm);
		return (isVerified > 0) ? true : false; 
	}


//	@Override
//	public void updateAuthenticationCode(String authenticationCode,Long customerId, String clientId) {
//		customerRepo.updateAuthenticationCode(authenticationCode,customerId, clientId);
//	}
	
//
//	@Override
//	public String getAuthenticationCode(String clientId, String clientSecret) {
//		Long authKeyId = getAuthIdByClientId(clientId);
//		return oauthKeyGrantTypeRepo.getAuthenticationCode(authKeyId, clientSecret);
//	}
//
//	@Override
//	public String getGrantType(String clientId, String clientSecret) {
//		Long authKeyId = getAuthIdByClientId(clientId);
//		return oauthKeyGrantTypeRepo.getGrantType(authKeyId, clientSecret);
//	}
//
//	private Long getAuthIdByClientId(String clientId) {
//		return oathKeyClientRepo.getAuthIdByClientId(clientId);
//	}

}
