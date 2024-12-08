package org.tipSell.eSecurity.payload.request;

import org.tipSell.custom.annotation.IsGrantType;
import org.tipSell.domain.enums.GrantTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientRequest {

	public interface OnCreate {}

	public interface OnUpdate {}

	@JsonIgnore
	private Long clientUid;
	
	@NotEmpty(groups = {OnCreate.class})
	@NotNull(groups = {OnCreate.class})
	private String clientID;
	
	@NotEmpty(groups = {OnCreate.class})
	@NotNull(groups = {OnCreate.class})
	@IsGrantType
	private String grantType;
	
	private Integer verifyMail;
	
	private String redirectURI;
	
//	@JsonIgnore
//	private Integer deleteFlag;

//	public ClientRequest(Long clientUid, Integer deleteFlag) {
//		super();
//		this.clientUid = clientUid;
//		this.deleteFlag = deleteFlag;
//	}
}
