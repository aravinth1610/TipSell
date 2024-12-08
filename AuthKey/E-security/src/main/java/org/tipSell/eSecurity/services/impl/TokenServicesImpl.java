package org.tipSell.eSecurity.services.impl;

import static org.tipSell.authKey.Constant.AuthKeyConstant.CLIENT_CREDENTIALS;
import static org.tipSell.authKey.Constant.AuthKeyConstant.PASSWORD;
import static org.tipSell.authKey.Constant.AuthKeyConstant.AUTHORIZATION_CODE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.authKey.Constant.AuthKeyConstant;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.eSecurity.domain.entity.UserRole;
import org.tipSell.eSecurity.domain.repository.AttributeRepository;
import org.tipSell.eSecurity.domain.repository.UserRepository;
import org.tipSell.eSecurity.domain.repository.OauthClientRepository;
import org.tipSell.eSecurity.domain.repository.OauthTokenRepository;
import org.tipSell.eSecurity.domain.repositoryDTO.AttributeRepositoryDTO;
import org.tipSell.eSecurity.domain.repositoryDTO.OauthTokenRepositoryDTO;
import org.tipSell.eSecurity.domain.repositoryDTO.UserRepositoryDTO;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.services.OauthClientDomain;
import org.tipSell.eSecurity.payload.response.TokenResponse;
import org.tipSell.eSecurity.services.TokenServices;
import org.tipSell.securityUtils.CryptoUtil;
import org.tipSell.securityUtils.EsecurityUtils;
import org.tipSell.securityUtils.TokenUtil;
import org.tipSell.uniCore.securityConstant.SecurityMessages;
import org.tipSell.validations.Services.RequestValidationsServices;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class TokenServicesImpl implements TokenServices {

	private final UserRepository userRepo;
	private final AttributeRepository attributeRepo;
	private final OauthClientRepository oAuthClientRepo;
	private final OauthTokenRepository oAuthTokenRepo;
	private final RequestValidationsServices validationServices;

	
	private Long client(String clientId) {
		return oAuthClientRepo.findClientUidByClientId(clientId);
	}

	private String generateTokenSecretKey() {
		return TokenUtil.tokenGeneratorSecretKey();
	}
	
	private <T> T getTokenGeneratedValue(Map<String, Object> map, String key, Class<T> clazz) {
	    Object value = map.get(key);
	    return clazz.isInstance(value) ? clazz.cast(value) : null;
	}

	private Map<String, String> clientProfiles(OauthClient client) {

		// Fetch attributes associated with the client
		Set<AttributeRepositoryDTO> attributes = attributeRepo.findAttributeByClient(client);
		
		// If attributes are null, return an empty map
		if (attributes == null || attributes.isEmpty()) {
			return new HashMap<>();
		}

		// Prepare profiles map from attributes
		Map<String, String> profiles = new HashMap<>();
		for (AttributeRepositoryDTO attribute : attributes) {
			profiles.put(attribute.getKey(), attribute.getValue()); // Use attribute value here
		}
			
		return profiles;

	}

	private Map<String,Object> tokenGeneration(String clientId, GrantTypes grantType, Object subject, String authorities) {
		String subjectName = subject.toString();
	
		Long clientUid = client(clientId);
		OauthClient client = new OauthClient(clientUid);
        Map<String, String> profiles = clientProfiles(client);

		Map<String, Object> claims =new HashMap<>();  //Map.of(AuthKeyConstant.CLAIM_GRANTYPE, grantType, AuthKeyConstant.AUTHORITIES,authorities);
		claims.put(AuthKeyConstant.CLAIM_GRANTYPE, grantType);
		claims.put(AuthKeyConstant.AUTHORITIES,authorities);
		
		
		if (!profiles.isEmpty())
			claims.put(AuthKeyConstant.CLAIM_PROFILES, profiles);

//		OauthTokenRepositoryDTO tokenRepositoryDTO =  oAuthTokenRepo.findAccessAndRefrershTokenByClient(client).orElse(null);
//		
//		if(null != tokenRepositoryDTO) {
//			 accessTokenExp = tokenRepositoryDTO.getAccessTokenExp(); 
//			 accessTokenUnit =  tokenRepositoryDTO.getAccessTokenUnit();
//
//		}

		Optional<OauthTokenRepositoryDTO> tokenRepositoryDTO =  oAuthTokenRepo.findAccessAndRefrershTokenByClient(client);
		
		Long accessTokenExp = tokenRepositoryDTO.map(OauthTokenRepositoryDTO::getAccessTokenExp).orElse(0L);
		String accessTokenUnit = tokenRepositoryDTO.map(OauthTokenRepositoryDTO::getAccessTokenUnit).orElse("");
		
		String token = TokenUtil.generateToken(subjectName, claims, accessTokenExp, accessTokenUnit);

		 return Map.of("token", token, "tokenRepositoryDTO", tokenRepositoryDTO);
	}

	private String getGrantedAuthorities(Set<?> authorities) {
		System.out.println(authorities);
		Set<String> grantedAuthorities = new HashSet<>();
		for (Object authority : authorities) {
			if (authority instanceof UserRole) {
				grantedAuthorities.add(((UserRole) authority).getRole());
			} else if (authority instanceof String) {
				grantedAuthorities.add((String) authority);
			} else {
				throw new IllegalArgumentException("Unsupported authority type: " + authority.getClass().getName());
			}
		}
		return String.join(",", grantedAuthorities);
	}

	private TokenResponse ClientCredentials(String clientId, GrantTypes grantType, String clientSecret) {
		Map<String, Object> tokenGeneration = tokenGeneration(clientId, grantType, clientId, "access_token");
		String token = getTokenGeneratedValue(tokenGeneration, "token", String.class);
    	return new TokenResponse(null, token, null);
	}

	private TokenResponse password(String clientId, GrantTypes grantType, String userName, String password) {
		UserRepositoryDTO users = validationServices.isAuthorizedUserExists(userName, password,AuthKeyConstant.CLIENT_USER);
		
		Map<String, Object> tokenGeneration = tokenGeneration(clientId, grantType, users.getUid(), getGrantedAuthorities(users.getRoles()));
	
		String token = getTokenGeneratedValue(tokenGeneration, "token", String.class);
		OauthTokenRepositoryDTO tokenRepositoryDTO = getTokenGeneratedValue(tokenGeneration, "tokenRepositoryDTO", OauthTokenRepositoryDTO.class);
		
		Map<String, Object> refreshTokenClaims = Map.of(AuthKeyConstant.CLAIM_GRANTYPE, grantType);
		String refreshToken = TokenUtil.generateRefreshToken(clientId, refreshTokenClaims, tokenRepositoryDTO.getRefreshTokenExp(), tokenRepositoryDTO.getRefreshTokenUnit());
		return new TokenResponse(AuthKeyConstant.TOKEN_PREFIX, token, refreshToken);
	}

	private TokenResponse authorizationCode(String clientId, GrantTypes granttype, String authorizationCode) {
		String authCode = CryptoUtil.decrypt(authorizationCode);
		boolean isCodeValid = CryptoUtil.validateRandomKeyExpiration(authCode);
		if (isCodeValid) {
			UserRepositoryDTO users = userRepo.findUidAndRolesByAuthenticationCode(authCode);
			Map<String, Object> tokenGeneration = tokenGeneration(clientId, granttype, users.getUid(), getGrantedAuthorities(users.getRoles()));
			String token = getTokenGeneratedValue(tokenGeneration, "token", String.class);
			return new TokenResponse(AuthKeyConstant.TOKEN_PREFIX, token, null);
		} else {
			throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());
		}

	}

	@Override
	@Transactional(readOnly = true)
	public String generateTokenSecureKey() {
		try {
			return generateTokenSecretKey();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public TokenResponse tokenGenerate(String realm, String code, String cisiKey, String clientId,String clientSecret, String grantType, String username, String password) {

		GrantTypes grantTypeEnum = GrantTypes.fromGrantType(grantType);
		
		grantType = grantTypeEnum.getGrantValue();
		
		if (PASSWORD.equals(grantType)) {
			validationServices.isRealmExistsForClientIdAndSecret(realm, clientId, clientSecret, grantType);
			if (username == null || password == null)
				throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());

			return password(clientId, grantTypeEnum, username, password);
		} 
		  else if (CLIENT_CREDENTIALS.equals(grantType)) {
				validationServices.isRealmExistsForClientIdAndSecret(realm, clientId, clientSecret, grantType);
			if (clientId == null && clientSecret == null) 
				throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());
			
			return ClientCredentials(clientId, grantTypeEnum, clientSecret);
		}
		  else if (AUTHORIZATION_CODE.equals(grantType)) {
			if (clientId == null && clientSecret == null) 
				throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());
			
			Map<String, String> cisiValues = validationServices.isCisiKeyExists(realm, cisiKey, null);
			return authorizationCode(cisiValues.get("clientId"), grantTypeEnum, code);
		}
		  else {
				throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());			  
		  }

				
//		switch (grantType) {
//		
//		case "authorization_code":
//			if (code == null && cisiKey == null)
//				throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());
//
//			Map<String, String> cisiValues = validationServices.isCisiKeyExists(realm, cisiKey);
//			return authorizationCode(cisiValues.get("clientId"), grantTypeEnum, code);
//
//		case "password":
//			if (username == null || password == null)
//				throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());
//			
//			return password(clientId, grantTypeEnum, username, password);
//
//		case "client_credential":
//			if (clientId == null && clientSecret == null)
//				throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());
//
//			return ClientCredentials(clientId, grantTypeEnum, clientSecret);
//
//		default:
//			throw new AccessDeniedException(SecurityMessages.ACCESS_DENIED.message());
//		}
	}

}
