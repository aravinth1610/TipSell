package org.tipSell.eSecurity.controller;

import static org.tipSell.authKey.Constant.AuthKeyConstant.CLIENT_USER;


import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.custom.annotation.IsGrantType;
import org.tipSell.eSecurity.domain.repositoryDTO.UserRepositoryDTO;
import org.tipSell.eSecurity.payload.request.RegisterRequest;
import org.tipSell.eSecurity.services.EsecurityServices;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;
import org.tipSell.uniCore.payload.request.CustomerRegisterRequest;
import org.tipSell.uniCore.securityConstant.SecurityConstant;
import org.tipSell.validations.Services.RequestValidationsServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */
@RestController
@RequestMapping("/protocol/openid-connect/realms/{realm}")
@AllArgsConstructor
public class EsecurityContoller {

	private final EsecurityServices eSecurityServices;
	private final RequestValidationsServices validationServices;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/generate/secure-key")
	private final String generateSecureKey() {
		return eSecurityServices.generateSecureKey();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/URI-configuration/clients/{clientId}")
	private final ResponseEntityWrapper<?> cientURI(
			@IsEmpty @PathVariable(name = "realm", required = true) String realm,
			@IsEmpty @PathVariable(name = "clientId", required = true) String clientId, HttpServletRequest request) {
		validationServices.isRealmExistsForClientIdAndSecret(realm, clientId, null, null);
		return new ResponseEntityWrapper<>("Success", eSecurityServices.clientURIs(realm, clientId, request).toMap(),"Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/auth/code/cisi/{cisiKey}",consumes = "application/x-www-form-urlencoded;charset=UTF-8", produces = "application/json")
	private final ResponseEntityWrapper<?> generateAuthorizationCode(
			@IsEmpty @PathVariable(name = "realm", required = true) String realm,
			@IsEmpty @PathVariable(name = "cisiKey", required = true) String cisiKey,
			@IsEmpty String mail,
			@IsEmpty String password,
			@IsEmpty @IsGrantType  String grantType,
			@IsEmpty String state, HttpSession session) {

		UserRepositoryDTO user = validationServices.isAuthorizedUserExists(mail, password, CLIENT_USER);
		Map<String, String> cisiValues = validationServices.isCisiKeyExists(realm, cisiKey, grantType);
		String authCode = eSecurityServices.generateAuthenticationCode(user.getUid(), cisiValues.get("clientId"), state,session);

		return new ResponseEntityWrapper<>("Success", authCode, "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping("/register/cisi/{cisiKey}")
	private final void registration(
			@RequestBody @Valid RegisterRequest userRegister,
			@IsEmpty @PathVariable(name = "realm", required = true) String realm,
			@IsEmpty @PathVariable(name = "cisiKey", required = true) String cisiKey) {

		Map<String, String> cisiValues = validationServices.isCisiKeyExists(realm, cisiKey, null);
		eSecurityServices.customerRegister(userRegister, cisiValues.get("clientId"));
	}	

}
