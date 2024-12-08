package org.tipSell.oAuthClient.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.eSecurity.domain.entity.Attribute;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.repository.AttributeRepository;
import org.tipSell.eSecurity.domain.repositoryDTO.AttributeRepositoryDTO;
import org.tipSell.eSecurity.mapper.AttributeMapper;
import org.tipSell.eSecurity.payload.request.AttributeRequest;
import org.tipSell.oAuthClient.services.AttributeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AttributeServiceImpl implements AttributeService {
	
	private final AttributeRepository attributeRepository;
	
	private final AttributeMapper attributeMapper;
	
	private OauthClient client(Long clientUid) {
		return new OauthClient(clientUid);
	}
	
	public void createAttribute(Set<AttributeRequest> attributes, Long clientUid) {
		
	   List<Attribute> listOfAttributes = attributeMapper.listOfAttribute(attributes, clientUid);
	   attributeRepository.saveAll(listOfAttributes);
	}
	
	public Set<AttributeRepositoryDTO> attributesDetails(Long clientUid){
		return attributeRepository.findAttributeByClient(client(clientUid));
	}
	
	public AttributeRepositoryDTO attributeByClientUidAndAttributUid(Long clientUid, Long attributeUid) {
		return attributeRepository.findAttributeByClientAndUid(client(clientUid), attributeUid);
	}
	
	public void updateAttribute(AttributeRequest attribute, Long clientUid, Long attributeUid) {
		attribute.setAttributeUid(attributeUid);
		Attribute attributeData = attributeMapper.attribute(attribute, clientUid);
		
		attributeRepository.save(attributeData);
	}
		
}
