package org.tipSell.oAuthClient.services;

import java.util.Set;

import org.tipSell.eSecurity.domain.repositoryDTO.AttributeRepositoryDTO;
import org.tipSell.eSecurity.payload.request.AttributeRequest;

public interface AttributeService {

	void createAttribute(Set<AttributeRequest> attributes, Long clientUid);
	
	Set<AttributeRepositoryDTO> attributesDetails(Long clientUid);
	
	AttributeRepositoryDTO attributeByClientUidAndAttributUid(Long clientUid, Long attributeUid);
	
	void updateAttribute(AttributeRequest attribute, Long clientUid, Long attributeUid);
	
}
