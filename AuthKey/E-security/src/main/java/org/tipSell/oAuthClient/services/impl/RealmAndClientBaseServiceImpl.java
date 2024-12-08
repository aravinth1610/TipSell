package org.tipSell.oAuthClient.services.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.domain.repository.OauthClientRepository;
import org.tipSell.eSecurity.domain.repository.OauthRealmRepository;
import org.tipSell.eSecurity.domain.repositoryDTO.OAuthClientRepositoryDTO;
import org.tipSell.eSecurity.domain.repositoryDTO.OauthRealmRepositoryDTO;
import org.tipSell.eSecurity.mapper.ClientMapper;
import org.tipSell.eSecurity.payload.request.ClientRequest;
import org.tipSell.oAuthClient.services.RealmAndClientBaseService;
import org.tipSell.uniCore.customeExceptions.CommonCaseException;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RealmAndClientBaseServiceImpl implements RealmAndClientBaseService {

	private OauthRealmRepository oAuthRealmRepo;

	private OauthClientRepository oAuthClientRepo;

	private ClientMapper clientMapper;

	public Set<OauthRealmRepositoryDTO> allRealms() {
		return oAuthRealmRepo.getAllRealmUidAndRealm();
	}

	public Set<OAuthClientRepositoryDTO> allClientByRealmUid(Long realmUid) {

		return oAuthClientRepo.getAllClientByRealm(new OauthRealm(realmUid));
	}

	public OauthRealmRepositoryDTO realmsByRealmUid(Long realmUid) {
		return oAuthRealmRepo.getRealmByReamUid(realmUid)
				.orElseThrow(() -> new CommonCaseException("Realm with ID " + realmUid + " not found."));
	}

	public OAuthClientRepositoryDTO clientByClientUid(Long realmUid, Long clientUid) {
		return oAuthClientRepo.clientByRealmAndClientUid(new OauthRealm(realmUid), clientUid)
				.orElseThrow(() -> new CommonCaseException("Client with ID " + clientUid + " not found."));
	}

	public void updateRealmByRealmUid(Long realmUid, String realm) {
		OauthRealm oAuthRealm = new OauthRealm(realmUid, realm);

		oAuthRealmRepo.save(oAuthRealm);
	}

	public void updateClientByClientUid(Long realmUid, Long clientId, ClientRequest clientRequest) {
		clientRequest.setClientUid(clientId);
		GrantTypes grantTypeEnum = GrantTypes.fromGrantType(clientRequest.getGrantType());

		OauthClient client = clientMapper.clientMapperCredential(clientRequest, realmUid, grantTypeEnum);
		oAuthClientRepo.save(client);
	}

	public void deleteRealmByRealmUid(Long realmUid) {
//		 oAuthRealmRepo.deleteRealm(realmUid)
//				.orElseThrow(() -> new RuntimeException());
	}

	public void deleteClientByClientUid(Long realmUid, Long clientUid) {
//		OAuthClientRepositoryDTO clientDTO = oAuthClientRepo.deleteClientByClientUid(clientUid)
//				.orElseThrow(() -> new RuntimeException("Client with ID  not found."));
		
//		OauthClient client = clientMapper.deleteClientMapper(clientDTO);
//		client.setDeleteFlag(1);
//		oAuthClientRepo.save(client);
	}

}
