package org.tipSell.eSecurity.domain.repositoryDTO;

import org.tipSell.domain.enums.Unit;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface OauthTokenRepositoryDTO {

	long getAccessTokenExp();
	
	String getAccessTokenUnit();

	long getRefreshTokenExp();

	String getRefreshTokenUnit();

}
