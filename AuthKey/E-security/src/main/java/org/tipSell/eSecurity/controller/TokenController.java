package org.tipSell.eSecurity.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.custom.annotation.IsGrantType;
import org.tipSell.eSecurity.payload.response.TokenResponse;
import org.tipSell.eSecurity.services.EsecurityServices;
import org.tipSell.eSecurity.services.TokenServices;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;
import org.tipSell.validations.Services.RequestValidationsServices;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/protocol/openid-connect/realms/{realm}")
@AllArgsConstructor
public class TokenController {

	private final TokenServices tokenServices;
	
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value ="/token/cisi/{cisiKey}",consumes = "application/x-www-form-urlencoded;charset=UTF-8", produces = "application/json")
	private final ResponseEntityWrapper<?>  token(@IsEmpty @PathVariable(name = "realm", required = true) String realm,
			@IsEmpty @PathVariable(name = "cisiKey", required = true) String cisiKey,
			@IsEmpty @RequestHeader(name = "code", required = true) String code) {

		TokenResponse token = tokenServices.tokenGenerate(realm, code, cisiKey, null, null, "authorization_code", null, null);
		return new ResponseEntityWrapper<>("Success",token,"Operation completed successfully.");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value ="/token",consumes = "application/x-www-form-urlencoded;charset=UTF-8", produces = "application/json")
	private final ResponseEntityWrapper<?>  auth(
			@IsEmpty @PathVariable(name = "realm", required = true) String realm,
			String clientId,
			String clientSecret,
			@IsEmpty @IsGrantType String grantType,
			String username,
			String password ) {
				
		
		TokenResponse token = tokenServices.tokenGenerate(realm, null, null, clientId, clientSecret, grantType, username, password);
		return new ResponseEntityWrapper<>("Success",token,"Operation completed successfully.");
	}

	
}
