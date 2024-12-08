package org.tipSell.oAuthClient.services;

import java.util.Optional;
import java.util.Set;

import org.tipSell.eSecurity.domain.repositoryDTO.RolesRepositoryDTO;
import org.tipSell.eSecurity.payload.request.RoleRequest;

public interface RoleService {

	void createRole(Long clientUid, Set<RoleRequest> roles);

	Set<RolesRepositoryDTO> allRolesByClientId(Long clientUid);
	
	RolesRepositoryDTO roleByUid(Long clientUid,Long roleUid);
	
	void updateRoleByClient(Long roleUid, RoleRequest roles);
	
	void deleteRole(Long roleUid);
	
}
