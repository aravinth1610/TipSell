package org.tipSell.oAuthClient.services.impl;

import static org.tipSell.authKey.Constant.AuthKeyConstant.CLIENT_CREDENTIALS;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.authKey.Constant.AuthKeyConstant;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.domain.enums.Unit;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.entity.OauthRealm;
import org.tipSell.eSecurity.domain.entity.OauthToken;
import org.tipSell.eSecurity.domain.repository.OauthClientRepository;
import org.tipSell.eSecurity.domain.repository.OauthRealmRepository;
import org.tipSell.eSecurity.domain.services.OauthClientDomain;
import org.tipSell.eSecurity.mapper.ClientMapper;
import org.tipSell.eSecurity.payload.request.ClientRequest;
import org.tipSell.oAuthClient.services.RealmAndClientProtocolService;
import org.tipSell.securityUtils.CryptoUtil;
import org.tipSell.securityUtils.EsecurityUtils;
import org.tipSell.uniCore.securityConstant.SecurityConstant;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RealmAndClientServiceImpl implements RealmAndClientProtocolService {

	private final OauthClientRepository oathKeyClientRepo;

	private final OauthRealmRepository oAuthRealmRepo;
	
	private final ClientMapper clientMapper;
	
	@Override
	public OauthRealm createRealmAndClient(String realm) {
		OauthRealm svdrealm = oAuthRealmRepo.save(new OauthRealm(realm));
		return svdrealm;
	}

	@Override
	public OauthClient createClientId(ClientRequest clientRequest, String realm) {
		Long realmUid = oAuthRealmRepo.findRealmUidByRealm(realm);
		GrantTypes grantTypeEnum = GrantTypes.fromGrantType(clientRequest.getGrantType());
		
		OauthClient client;
		if (grantTypeEnum.getGrantValue().equals(CLIENT_CREDENTIALS)) {
			client =clientMapper.clientMapperCredential(clientRequest, realmUid, grantTypeEnum);
		}else {
			client =clientMapper.clientMapper(clientRequest, realmUid, grantTypeEnum);
		}

		return oathKeyClientRepo.save(client);
	}

}
