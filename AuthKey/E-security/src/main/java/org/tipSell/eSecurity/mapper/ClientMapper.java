package org.tipSell.eSecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tipSell.authKey.Constant.AuthKeyConstant;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.domain.enums.Unit;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.domain.entity.OauthToken;
import org.tipSell.eSecurity.domain.repositoryDTO.OAuthClientRepositoryDTO;
import org.tipSell.eSecurity.payload.request.ClientRequest;
import org.tipSell.securityUtils.EsecurityUtils;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {

	private  Long tokenValidity = AuthKeyConstant.ACCESS_TOKEN_VALIDITY;

	private Long refreshValidity = AuthKeyConstant.REFRESH_TOKEN_VALIDITY;

	private Unit units = Unit.days;
		
	@Mappings({ 
//		@Mapping(target = "deleteFlag", defaultValue = "0"),
		@Mapping(target = "token", ignore = true),
		@Mapping(target = "attribute", ignore = true),
		@Mapping(target = "realm", expression = "java(mapRealm(realmUid))"),
		@Mapping(target = "clientSecret", expression = "java(generateSecretKey())"),
		@Mapping(target = "grantType", expression = "java(mapGrantType(grantTypeEnum))")
		})
	public abstract OauthClient clientMapperCredential(ClientRequest request, Long realmUid, GrantTypes grantTypeEnum);
	
	@Mappings({ 
//		@Mapping(target = "deleteFlag", defaultValue = "0"),
		@Mapping(target = "attribute", ignore = true),
		@Mapping(target = "token", expression = "java(mapToken())"),
		@Mapping(target = "realm", expression = "java(mapRealm(realmUid))"),
		@Mapping(target = "clientSecret", expression = "java(generateSecretKey())"),
		@Mapping(target = "grantType", expression = "java(mapGrantType(grantTypeEnum))")
		})
	public abstract OauthClient clientMapper(ClientRequest request, Long realmUid, GrantTypes grantTypeEnum);
	
	@Mappings({ 
	@Mapping(target = "token", ignore = true),
	@Mapping(target = "attribute", ignore = true)
	})
	public abstract OauthClient clientMapper(ClientRequest request, Long realmUid);
	
	
	public abstract OauthClient clientMapper(OAuthClientRepositoryDTO oauthClientRepositoryDTO);
	
	
//	public abstract OauthClient deleteClientMapper(Integer deleteFlag);
	
	protected String generateSecretKey() {
		return EsecurityUtils.generateClientSecret();
	}
	
	protected OauthRealm mapRealm(Long realmUid) {
		return new OauthRealm(realmUid);
	}
	
	protected OauthToken mapToken() {
		return new OauthToken(tokenValidity, units, refreshValidity, units);
	}
	
	protected GrantTypes mapGrantType(GrantTypes grantTypeEnum) {
		return grantTypeEnum;
	}

	
}
