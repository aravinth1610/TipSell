package org.tipSell.oAuthClient.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.oAuthClient.services.RoleService;
import org.tipSell.oAuthClient.services.TokenBaseServices;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/clients/{clientUid}/token")
@AllArgsConstructor
public class TokenBaseController {
	
	private final TokenBaseServices tokenBaseService;
	
//	@ResponseStatus(HttpStatus.OK)
//	@PostMapping("/clients/{clientId}")
//	private final ResponseEntityWrapper<?> createToken(@IsEmpty @PathVariable(name = "clientId", required = true) String clientId,
//			@RequestParam(name="access_token_exp") Long accessTokenExp, 
//			@RequestParam(name="access_token_unit") String accessTokenUnit,
//			@RequestParam(name="refresh_token_exp") Long refreshTokenExp,
//			@RequestParam(name="refresh_oken_unit") String refreshTokenUnit) {
//		return new ResponseEntityWrapper<>("Success",  "Operation completed successfully.");
//	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	private final ResponseEntityWrapper<?> tokenDetails(@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid) {
		return new ResponseEntityWrapper<>("Success", tokenBaseService.tokenDetails(clientUid),  "Operation completed successfully.");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping
	private final ResponseEntityWrapper<?> updateToken(@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid,
//			@RequestParam(name="access_token_exp" ,required = false) Long accessTokenExp, 
//			@RequestParam(name="access_token_unit" ,required = false) String accessTokenUnit,
			@RequestParam(name="refresh_token_exp" ,required = true) Long refreshTokenExp,
			@RequestParam(name="refresh_token_unit" ,required = true) String refreshTokenUnit) {
		tokenBaseService.updateToken(clientUid, refreshTokenExp, refreshTokenUnit);
		return new ResponseEntityWrapper<>("Success",  "Operation completed successfully.");
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping
	private final ResponseEntityWrapper<?> deleteToken(@IsEmpty @PathVariable(name = "clientUid", required = true) String clientId) {
		return new ResponseEntityWrapper<>("Success",  "Operation completed successfully.");
	}
	

	
}
