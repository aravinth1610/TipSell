package org.tipSell.eSecurity.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.repositoryDTO.OAuthClientRepositoryDTO;
import org.tipSell.eSecurity.payload.request.ClientRequest;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T14:40:02+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Ubuntu)"
)
@Component
public class ClientMapperImpl extends ClientMapper {

    @Override
    public OauthClient clientMapperCredential(ClientRequest request, Long realmUid, GrantTypes grantTypeEnum) {
        if ( request == null && realmUid == null && grantTypeEnum == null ) {
            return null;
        }

        OauthClient oauthClient = new OauthClient();

        oauthClient.setRealm( mapRealm(realmUid) );
        oauthClient.setClientSecret( generateSecretKey() );
        oauthClient.setGrantType( mapGrantType(grantTypeEnum) );

        return oauthClient;
    }

    @Override
    public OauthClient clientMapper(ClientRequest request, Long realmUid, GrantTypes grantTypeEnum) {
        if ( request == null && realmUid == null && grantTypeEnum == null ) {
            return null;
        }

        OauthClient oauthClient = new OauthClient();

        oauthClient.setToken( mapToken() );
        oauthClient.setRealm( mapRealm(realmUid) );
        oauthClient.setClientSecret( generateSecretKey() );
        oauthClient.setGrantType( mapGrantType(grantTypeEnum) );

        return oauthClient;
    }

    @Override
    public OauthClient clientMapper(ClientRequest request, Long realmUid) {
        if ( request == null && realmUid == null ) {
            return null;
        }

        OauthClient oauthClient = new OauthClient();

        return oauthClient;
    }

    @Override
    public OauthClient clientMapper(OAuthClientRepositoryDTO oauthClientRepositoryDTO) {
        if ( oauthClientRepositoryDTO == null ) {
            return null;
        }

        OauthClient oauthClient = new OauthClient();

        oauthClient.setUpdatedOn( oauthClientRepositoryDTO.getUpdatedOn() );
        oauthClient.setUpdatedBy( oauthClientRepositoryDTO.getUpdatedBy() );
        oauthClient.setDeleteFlag( oauthClientRepositoryDTO.getDeleteFlag() );
        oauthClient.setClientUid( oauthClientRepositoryDTO.getClientUid() );
        oauthClient.setClientID( oauthClientRepositoryDTO.getClientID() );
        if ( oauthClientRepositoryDTO.getGrantType() != null ) {
            oauthClient.setGrantType( Enum.valueOf( GrantTypes.class, oauthClientRepositoryDTO.getGrantType() ) );
        }
        oauthClient.setClientSecret( oauthClientRepositoryDTO.getClientSecret() );
        oauthClient.setVerifyMail( oauthClientRepositoryDTO.getVerifyMail() );

        return oauthClient;
    }
}
