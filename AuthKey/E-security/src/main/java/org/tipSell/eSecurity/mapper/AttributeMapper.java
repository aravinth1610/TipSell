package org.tipSell.eSecurity.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tipSell.eSecurity.domain.entity.Attribute;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.payload.request.AttributeRequest;

@Mapper(componentModel = "spring")
public abstract class AttributeMapper {

	@Mapping(target = "client", expression = "java(mapClient(clientUid))")
	public abstract Attribute attribute(AttributeRequest attributeRequest, Long clientUid);
	
	public  List<Attribute> listOfAttribute(Set<AttributeRequest> attributeRequest, Long clientUid) {
		return attributeRequest.stream().map(request -> attribute(request, clientUid)).collect(Collectors.toList());
	}
	
	
	protected OauthClient mapClient(Long clientUid) {
		return new OauthClient(clientUid);
	}

	
}
