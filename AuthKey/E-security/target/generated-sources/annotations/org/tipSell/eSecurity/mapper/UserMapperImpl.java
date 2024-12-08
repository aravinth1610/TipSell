package org.tipSell.eSecurity.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.tipSell.eSecurity.domain.entity.User;
import org.tipSell.eSecurity.payload.request.RegisterRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T14:40:02+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Ubuntu)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public User userRegister(RegisterRequest request, Long clientUID) {
        if ( request == null && clientUID == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( passwordEncoder.encode(request.getPassword()) );
        user.setRoles( setRole() );
        user.setClient( mapClient(clientUID) );

        return user;
    }
}
