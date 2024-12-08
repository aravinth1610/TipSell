package org.tipSell.eSecurity.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.tipSell.eSecurity.domain.entity.OauthToken;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T14:40:02+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Ubuntu)"
)
@Component
public class TokenMapperImpl extends TokenMapper {

    @Override
    public OauthToken tokenMapper(Long clientUID, Long accessTokenExp, String accessTokenUnit, Long refreshTokenExp, String refreshTokenUnit) {
        if ( clientUID == null && accessTokenExp == null && accessTokenUnit == null && refreshTokenExp == null && refreshTokenUnit == null ) {
            return null;
        }

        OauthToken oauthToken = new OauthToken();

        oauthToken.setTokenUid( mapClient(clientUID) );
        oauthToken.setAccessTokenExpiration( mapAccessTokenExp(accessTokenExp) );
        oauthToken.setAccessTokenUnit( mapAccessTokenUnit(accessTokenUnit) );
        oauthToken.setRefreshTokenExpiration( mapRefreshTokenExp(refreshTokenExp) );
        oauthToken.setRefreshTokenUnit( mapRefreshTokenUnit(refreshTokenUnit) );

        return oauthToken;
    }
}
