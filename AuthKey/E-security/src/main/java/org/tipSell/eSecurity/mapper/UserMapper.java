package org.tipSell.eSecurity.mapper;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.tipSell.eSecurity.domain.entity.User;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.UserRole;
import org.tipSell.eSecurity.payload.request.RegisterRequest;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Mappings({
	@Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))"),
	@Mapping(target = "roles", expression = "java(setRole())"), 
	@Mapping(target = "client", expression = "java(mapClient(clientUID))") 
	 })
	public abstract User userRegister(RegisterRequest request,Long clientUID);

	 // Custom method to map clientUID to OauthClient
    protected OauthClient mapClient(Long clientUID) {
        return new OauthClient(clientUID);  
    }
    
    protected Set<UserRole> setRole() {
    	UserRole role = new UserRole();
        role.setRole("USER");  
        Set<UserRole> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }
	
}
