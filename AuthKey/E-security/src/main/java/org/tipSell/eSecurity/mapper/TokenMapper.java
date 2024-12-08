package org.tipSell.eSecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tipSell.domain.enums.Unit;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthToken;
import org.tipSell.eSecurity.domain.entity.Roles;
import org.tipSell.eSecurity.payload.request.RoleRequest;

@Mapper(componentModel = "spring")
public abstract class TokenMapper {

	@Mappings({ 
		@Mapping(target = "tokenUid", expression = "java(mapClient(clientUID))"),
		@Mapping(target = "accessTokenExpiration", expression = "java(mapAccessTokenExp(accessTokenExp))"),
		@Mapping(target = "accessTokenUnit", expression = "java(mapAccessTokenUnit(accessTokenUnit))"),
		@Mapping(target = "refreshTokenExpiration", expression = "java(mapRefreshTokenExp(refreshTokenExp))"),
		@Mapping(target = "refreshTokenUnit", expression = "java(mapRefreshTokenUnit(refreshTokenUnit))")
		})
	public abstract OauthToken tokenMapper(Long clientUID, Long accessTokenExp, String accessTokenUnit, Long refreshTokenExp, String refreshTokenUnit);
	
	protected Long mapAccessTokenExp(Long accessTokenExp) {
		return accessTokenExp;
	}
	
	protected Unit mapAccessTokenUnit(String accessTokenUnit) {
		return unitValue(accessTokenUnit);
	}
	
	protected Long mapRefreshTokenExp(Long refreshTokenExp) {
		return refreshTokenExp;
	}
	
	protected Unit mapRefreshTokenUnit(String refreshTokenUnit) {
		return unitValue(refreshTokenUnit);
	}
	
	protected Long mapClient(Long clientUID) {
		return clientUID;
	}
	
	protected Unit unitValue(String unit) {
		return Unit.fromUnit(unit);
	}
	
}
