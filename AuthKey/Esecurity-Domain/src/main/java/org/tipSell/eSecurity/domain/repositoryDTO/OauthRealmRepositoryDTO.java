package org.tipSell.eSecurity.domain.repositoryDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface OauthRealmRepositoryDTO {

	Long getRealmUid();
	
	String getRealm();
	
}
