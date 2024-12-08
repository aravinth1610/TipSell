package org.tipSell.eSecurity.domain.repositoryDTO;

import java.util.Set;

import org.tipSell.eSecurity.domain.entity.UserRole;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface UserRepositoryDTO {

	String getGmail();

	Long getUid();

	String getPassword();
	
	Set<UserRole> getRoles();

}
