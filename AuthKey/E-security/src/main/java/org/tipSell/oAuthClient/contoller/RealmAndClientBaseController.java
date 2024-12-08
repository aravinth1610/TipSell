package org.tipSell.oAuthClient.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tipSell.custom.annotation.IsEmpty;
import org.tipSell.custom.annotation.IsGrantType;
import org.tipSell.eSecurity.payload.request.ClientRequest;
import org.tipSell.oAuthClient.services.RealmAndClientBaseService;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
//@RequestMapping("/client")
@AllArgsConstructor
public class RealmAndClientBaseController {
	
	private final RealmAndClientBaseService realmAndClientBaservices;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/realms")
	private final ResponseEntityWrapper<?> allRealms() {
		
		return new ResponseEntityWrapper<>("Success", realmAndClientBaservices.allRealms(),  "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/realms/{realmUid}/clients")
	private final ResponseEntityWrapper<?> allClientsByRealmUid(@IsEmpty @PathVariable(name = "realmUid", required = true) Long realmUid) {

		return new ResponseEntityWrapper<>("Success", realmAndClientBaservices.allClientByRealmUid(realmUid), "Operation completed successfully.");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/realms/{realmUid}")
	private final ResponseEntityWrapper<?> realms(@IsEmpty @PathVariable(name = "realmUid", required = true) Long realmUid) {

		return new ResponseEntityWrapper<>("Success", realmAndClientBaservices.realmsByRealmUid(realmUid), "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/realms/{realmUid}/clients/{clientUid}")
	private final ResponseEntityWrapper<?> clients(@IsEmpty @PathVariable(name = "realmUid", required = true) Long realmUid, @IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid) {
		return new ResponseEntityWrapper<>("Success", realmAndClientBaservices.clientByClientUid(realmUid, clientUid), "Operation completed successfully.");
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/realms/{realmUid}")
	private final ResponseEntityWrapper<?> updateRealm(@IsEmpty @PathVariable(name = "realmUid", required = true) Long realmUid,@IsEmpty @RequestParam(name="realm") String realm) {
		realmAndClientBaservices.updateRealmByRealmUid(realmUid, realm);
		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/realms/{realmUid}/clients/{clientUid}")
	private final ResponseEntityWrapper<?> updateClient(@IsEmpty @PathVariable(name = "realmUid", required = true) Long realmUid, @IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid, @Valid @RequestBody ClientRequest clientRequest) {
		realmAndClientBaservices.updateClientByClientUid(realmUid, clientUid, clientRequest);
		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}
		
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/realms/{realmUid}")
	private final ResponseEntityWrapper<?> deleteRealm(@IsEmpty @PathVariable(name = "realmUid", required = true) Long realmUid) {
		realmAndClientBaservices.deleteRealmByRealmUid(realmUid);
		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/realms/{realmUid}/clients/{clientUid}")
	private final ResponseEntityWrapper<?> deleteClient(@IsEmpty @PathVariable(name = "realmUid", required = true) Long realmUid, @IsEmpty @PathVariable(name = "clientUid", required = true) Long clientUid) {
		realmAndClientBaservices.deleteClientByClientUid(realmUid, clientUid);
		return new ResponseEntityWrapper<>("Success", "Operation completed successfully.");
	}
	
}
