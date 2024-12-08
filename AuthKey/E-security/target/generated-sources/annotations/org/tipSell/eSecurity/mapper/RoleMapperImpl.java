package org.tipSell.eSecurity.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.tipSell.eSecurity.domain.entity.Roles;
import org.tipSell.eSecurity.payload.request.RoleRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T14:40:02+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Ubuntu)"
)
@Component
public class RoleMapperImpl extends RoleMapper {

    @Override
    public Roles roleMapper(RoleRequest request, Long clientUID) {
        if ( request == null && clientUID == null ) {
            return null;
        }

        Roles roles = new Roles();

        roles.setClient( mapClient(clientUID) );

        return roles;
    }

    @Override
    public Roles roleMapper(RoleRequest request) {
        if ( request == null ) {
            return null;
        }

        Roles roles = new Roles();

        return roles;
    }
}
