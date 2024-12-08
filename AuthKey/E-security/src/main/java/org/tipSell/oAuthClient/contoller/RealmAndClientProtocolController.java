package org.tipSell.oAuthClient.contoller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.custom.annotation.IsGrantType;
import org.tipSell.eSecurity.payload.request.ClientRequest;
import org.tipSell.eSecurity.payload.request.RegisterRequest;
import org.tipSell.oAuthClient.services.RealmAndClientProtocolService;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;
import org.tipSell.validations.Services.RequestValidationsServices;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/protocol/openid-connect/clients")
@AllArgsConstructor
public class RealmAndClientProtocolController {

	private final RealmAndClientProtocolService oAuthClientBaseServices;
	private final RequestValidationsServices validationServie;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/create/realms")
	private final ResponseEntityWrapper<?> createRealmAndClient(@IsEmpty @RequestParam(name="realm",required =  true) String realm)
	{
		   validationServie.isRealmExists(realm);
		   oAuthClientBaseServices.createRealmAndClient(realm);
		   
		   return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/realms/{realm}/create/clientId")
	private final ResponseEntityWrapper<?> createClientId(@IsEmpty @PathVariable(name="realm",required = true) String realm,@Valid @RequestBody ClientRequest clientRequest) 
	{
//		   validationServie.isClientsRequest(realm, clientId);  
		   oAuthClientBaseServices.createClientId(clientRequest, realm);
		   
		   return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}	
	
}
