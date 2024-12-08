package org.tipSell.oAuthClient.contoller;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.eSecurity.domain.repositoryDTO.RolesRepositoryDTO;
import org.tipSell.eSecurity.payload.request.RoleRequest;
import org.tipSell.eSecurity.services.EsecurityServices;
import org.tipSell.oAuthClient.services.RoleService;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;
import org.tipSell.validations.Services.RequestValidationsServices;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/clients/{clientUid}/roles")
@AllArgsConstructor
public class RoleController {

	private final RoleService roleServices;
	private final RequestValidationsServices requestValidationServices;

//	@GetMapping("/key/demo/sec")
//	public void getMethodName(@RequestHeader("X-User-ID") String profileId) {
//		System.out.println(profileId);
//		eSecurityServices.demoSec();
//	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	private final ResponseEntityWrapper<?> createRole(
			@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid,
			@Valid @RequestBody  Set<RoleRequest> roles) {  
		requestValidationServices.roleExists(roles, clientUid);
		roleServices.createRole(clientUid, roles);

		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	private final ResponseEntityWrapper<?> allRolesByClientId(
			@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid) {

		return new ResponseEntityWrapper<>("Success", roleServices.allRolesByClientId(clientUid), "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{roleUid}")
	private final ResponseEntityWrapper<?> roleByUid(
			@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid,
			@IsEmpty @PathVariable(name = "roleUid", required = true) Long roleUid) {

		return new ResponseEntityWrapper<>("Success", roleServices.roleByUid(clientUid, roleUid),
				"Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{roleUid}")
	private final ResponseEntityWrapper<?> updateRole(
			@IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid,
			@IsEmpty @PathVariable(name = "roleUid", required = true) Long roleUid,
			@Valid @RequestBody RoleRequest roleRequest) {
		
		requestValidationServices.roleExists(roleRequest, clientUid);
		
		roleServices.updateRoleByClient(roleUid, roleRequest);

		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{roleUid}")
	private final ResponseEntityWrapper<?> deleteRole(@IsEmpty @PathVariable(name = "roleUid", required = true) Long roleUid) {
		roleServices.deleteRole(roleUid);

		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}

}
