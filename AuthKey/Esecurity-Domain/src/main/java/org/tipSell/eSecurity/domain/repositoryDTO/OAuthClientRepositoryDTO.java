package org.tipSell.eSecurity.domain.repositoryDTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface OAuthClientRepositoryDTO {

	String getClientSecret();
	
	String getGrantType();
	
	Long getClientUid();
	
	String getClientID();
	
	Integer getVerifyMail();
	
	Date getUpdatedOn();
	
	Long getUpdatedBy();
	
	Integer getDeleteFlag();
	
}
