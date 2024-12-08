package org.tipSell.oAuthClient.services;

import org.tipSell.eSecurity.domain.repositoryDTO.OauthTokenRepositoryDTO;

public interface TokenBaseServices {

	OauthTokenRepositoryDTO tokenDetails(Long clientUid);
	
	void updateToken(Long clientUid, Long refreshTokenExp, String refreshTokenUnit);
	
}
