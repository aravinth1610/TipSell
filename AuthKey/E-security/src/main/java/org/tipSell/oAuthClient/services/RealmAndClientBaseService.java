package org.tipSell.oAuthClient.services;

import java.util.Set;

import org.tipSell.eSecurity.domain.repositoryDTO.OAuthClientRepositoryDTO;
import org.tipSell.eSecurity.domain.repositoryDTO.OauthRealmRepositoryDTO;
import org.tipSell.eSecurity.payload.request.ClientRequest;

public interface RealmAndClientBaseService {

	Set<OauthRealmRepositoryDTO> allRealms();
	
	Set<OAuthClientRepositoryDTO> allClientByRealmUid(Long realmUid);
	
	OauthRealmRepositoryDTO realmsByRealmUid(Long realmUid);
	
	OAuthClientRepositoryDTO clientByClientUid(Long realmUid, Long clientUid);
	
	void updateRealmByRealmUid(Long realmUid,String realm);
	
	void updateClientByClientUid(Long realmUid, Long clientId, ClientRequest clientRequest);
	
	void deleteRealmByRealmUid(Long realmUid);
	
	void deleteClientByClientUid(Long realmUid, Long clientUid);
}
