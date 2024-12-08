package org.tipSell.eSecurity.services.impl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.management.relation.Role;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tipSell.configure.UserServices;
import org.tipSell.domain.enums.GrantTypes;
import org.tipSell.eSecurity.domain.entity.OauthClient;
import org.tipSell.eSecurity.domain.repository.UserRepository;
import org.tipSell.eSecurity.domain.repository.OauthClientRepository;
import org.tipSell.eSecurity.domain.repositoryDTO.OAuthClientRepositoryDTO;
import org.tipSell.eSecurity.domain.repositoryDTO.UserRepositoryDTO;
import org.tipSell.eSecurity.domain.entity.User;
import org.tipSell.eSecurity.domain.services.OauthClientDomain;
import org.tipSell.eSecurity.mapper.UserMapper;
import org.tipSell.eSecurity.payload.request.RegisterRequest;
import org.tipSell.eSecurity.services.EsecurityServices;
import org.tipSell.securityUtils.CryptoUtil;
import org.tipSell.securityUtils.EsecurityUtils;
import org.tipSell.securityUtils.TransactionUtils;
import org.tipSell.uniCore.payload.request.CustomerRegisterRequest;
import org.tipSell.uniCore.securityConstant.SecurityConstant;
import org.tipSell.validations.Services.RequestValidationsServices;
import org.tipSell.uniCore.payload.request.CustomerRegisterRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */
@Service
@AllArgsConstructor
@Transactional
public class EsecurityServicesImpl implements EsecurityServices {

	private final OauthClientRepository oAuthKeyClientRepo;
	private final UserRepository userRepo;

	private final UserMapper userMapper;

	private void updateAuthenticationCode(String code, String clientId, Long customerId) {
		Long clientUID = oAuthKeyClientRepo.findClientUidByClientId(clientId);
		userRepo.updateAuthenticationCode(code, customerId, new OauthClient(clientUID));
	}

	@Override
	@Transactional(readOnly = true)
	public String generateSecureKey() {
		try {
			SecretKey key = CryptoUtil.generateKey();
			return CryptoUtil.encodeKey(key);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public JSONObject clientURIs(String realm, String clientId, HttpServletRequest request) {

		OAuthClientRepositoryDTO oAuthClientRepositoryDTO = oAuthKeyClientRepo.findSecretKeyByClient(clientId);

		String cisiKey = CryptoUtil.encrypt(clientId.concat(".").concat(oAuthClientRepositoryDTO.getClientSecret()));
		String configString = TransactionUtils.readConfigFile("static/grantTypeConfig/grantTypeConfig.json");
		String basePath = String.format("%s://%s:%d%s", request.getScheme(), request.getServerName(),request.getServerPort(), request.getContextPath());

		return TransactionUtils.replacePlaceholders(configString, realm, cisiKey,oAuthClientRepositoryDTO.getGrantType(), basePath.toString());

	}

	@Override
	public String generateAuthenticationCode(long userUid, String clientId, String state, HttpSession session) {

//		if (!grantType.equalsIgnoreCase("Code") && !isAuthClientIdExists(clientId) && !isValidState(state, session)) {
//			return "redirect:/error";
//		}

		String code = CryptoUtil.generateRandomKeyWithExpireDate();
		updateAuthenticationCode(code, clientId, userUid);
		String authorizationCode = CryptoUtil.encrypt(code);
		return URLEncoder.encode(authorizationCode, StandardCharsets.UTF_8);
	}

	@Override
	public void customerRegister(RegisterRequest userRegister, String clientId) {

		Long clientUID = oAuthKeyClientRepo.findClientUidByClientId(clientId);
		User user = userMapper.userRegister(userRegister, clientUID);

		userRepo.save(user);
	}

}
