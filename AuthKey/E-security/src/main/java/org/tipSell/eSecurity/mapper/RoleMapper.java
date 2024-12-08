package org.tipSell.eSecurity.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.Roles;
import org.tipSell.eSecurity.domain.entity.User;
import org.tipSell.eSecurity.payload.request.RegisterRequest;
import org.tipSell.eSecurity.payload.request.RoleRequest;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {
	@Mappings({ 
//	@Mapping(target = "deleteFlag", ignore = true),
	@Mapping(target = "client", expression = "java(mapClient(clientUID))") 
	})
	public abstract Roles roleMapper(RoleRequest request, Long clientUID);

	public List<Roles> rolesMapperList(Set<RoleRequest> requests, Long clientUID) {
//		return requests.stream().map(request -> {
//			Roles role = roleMapper(request, clientUID);
//			role.setDefaultRole(request.getDefaultRole() == null ? 0 : 1);
//			return role;
//		}).collect(Collectors.toList());
		return requests.stream().map(request -> roleMapper(request, clientUID)).collect(Collectors.toList());
	}

//	@Mappings({ @Mapping(target = "deleteFlag", ignore = true)})
	public abstract Roles roleMapper(RoleRequest request);

	protected OauthClient mapClient(Long clientUID) {
		return new OauthClient(clientUID);
	}

}
