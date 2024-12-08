package org.tipSell.oAuthClient.services;

import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.payload.request.ClientRequest;

public interface RealmAndClientProtocolService {

	OauthRealm createRealmAndClient(String realm);
	
	OauthClient createClientId(ClientRequest clientRequest, String realm);
	
}
