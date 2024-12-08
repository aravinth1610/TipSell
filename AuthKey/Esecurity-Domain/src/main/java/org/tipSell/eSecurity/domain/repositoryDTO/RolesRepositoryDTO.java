package org.tipSell.eSecurity.domain.repositoryDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RolesRepositoryDTO {

	Long getRoleUid();

	String getRole();

	Boolean getDefaultRole();

}
