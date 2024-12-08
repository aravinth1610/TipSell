package org.tipSell.oAuthClient.contoller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.eSecurity.payload.request.AttributeRequest;
import org.tipSell.eSecurity.payload.request.RoleRequest;
import org.tipSell.oAuthClient.services.AttributeService;
import org.tipSell.oAuthClient.services.RoleService;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;
import org.tipSell.validations.Services.RequestValidationsServices;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/clients/{clientUid}/attribute")
@AllArgsConstructor
public class AttributeController {
	
	private final AttributeService attributeService;

	private final RequestValidationsServices requestValidationServices;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	private final ResponseEntityWrapper<?> createAttribute(
			@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid,
			@Valid @RequestBody   Set<AttributeRequest> attributes) {
		requestValidationServices.attributeExists(attributes, clientUid);
		
		attributeService.createAttribute(attributes, clientUid);
		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	private final ResponseEntityWrapper<?> allAttribute(@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid) {

		return new ResponseEntityWrapper<>("Success",attributeService.attributesDetails(clientUid), "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{attributeUid}")
	private final ResponseEntityWrapper<?> attributesByUid(@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid,
			@IsEmpty @PathVariable(name = "attributeUid", required = true) Long attributeUid) {

		return new ResponseEntityWrapper<>("Success",attributeService.attributeByClientUidAndAttributUid(clientUid, attributeUid), "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{attributeUid}")
	private final ResponseEntityWrapper<?> updateAttribute(
			@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid,
			@IsEmpty @PathVariable(name = "attributeUid", required = true) Long attributeUid,
			@Valid @RequestBody AttributeRequest attributeRequest) {
	
		attributeService.updateAttribute(attributeRequest, clientUid, attributeUid);
		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{attributeUid}")
	private final ResponseEntityWrapper<?> deleteRole(@IsEmpty @PathVariable(name = "attributeUid", required = true) Long attributeUid) {
		
		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}

	
}
