package org.tipSell.eSecurity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tipSell.eSecurity.services.EsecurityServices;
import org.tipSell.eSecurity.services.TokenServices;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@AllArgsConstructor
@RequestMapping("/protocol/openid-connect/key")
public class SecureKeyController {

	private final EsecurityServices eSecurityServices;
	private final TokenServices tokenServices;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/generate/secure-key")
	private final ResponseEntityWrapper<?> generateSecureKey() {
		return new ResponseEntityWrapper<>("Success", eSecurityServices.generateSecureKey(),"Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/generate/token-secure-key")
	private final ResponseEntityWrapper<?> generateTokenSecureKey() {
		return new ResponseEntityWrapper<>("Success", tokenServices.generateTokenSecureKey(),"Operation completed successfully.");
	}

}
