package org.tipSell.oAuthClient.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.domain.enums.Unit;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthToken;
import org.tipSell.eSecurity.domain.repository.OauthClientRepository;
import org.tipSell.eSecurity.domain.repository.OauthRealmRepository;
import org.tipSell.eSecurity.domain.repository.OauthTokenRepository;
import org.tipSell.eSecurity.domain.repositoryDTO.OauthTokenRepositoryDTO;
import org.tipSell.eSecurity.mapper.ClientMapper;
import org.tipSell.eSecurity.mapper.TokenMapper;
import org.tipSell.oAuthClient.services.TokenBaseServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class TokenBaseServicesImpl implements TokenBaseServices {

	private OauthTokenRepository oAuthTokenRepository;
	private final OauthClientRepository oAuthClientRepo;
	
	private final TokenMapper tokenMapper;
	
	private Long client(String clientId) {
		return oAuthClientRepo.findClientUidByClientId(clientId);
	}
	
	public OauthTokenRepositoryDTO tokenDetails(Long clientUid) {
//		Long clientUid = client(clientId);
		OauthClient client = new OauthClient(clientUid);
		 return oAuthTokenRepository.findAccessAndRefrershTokenByClient(client).orElseThrow(() -> new RuntimeException());	
	}
	
	public void updateToken(Long clientUid, Long accessTokenExp, String accessTokenUnit, Long refreshTokenExp, String refreshTokenUnit) {
//		Long clientUidd = client(clientUid);
		Long tokenUid = oAuthTokenRepository.findTokenUidByClientFk(new OauthClient(clientUid)).orElseThrow(() -> new EntityNotFoundException());
		System.out.println(tokenUid);
		OauthToken token =	tokenMapper.tokenMapper(tokenUid, accessTokenExp, accessTokenUnit, refreshTokenExp, refreshTokenUnit);
		
//		OauthToken token = new OauthToken(tokenUid, accessTokenExp, Unit.fromUnit(accessTokenUnit) , refreshTokenExp, Unit.fromUnit(refreshTokenUnit));
		oAuthTokenRepository.save(token);
	}

	@Override
	public void updateToken(Long clientUid, Long refreshTokenExp, String refreshTokenUnit) {
		// TODO Auto-generated method stub
		
	}
}
