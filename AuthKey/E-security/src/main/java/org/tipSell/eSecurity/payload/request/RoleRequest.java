package org.tipSell.eSecurity.payload.request;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
//@IsRole(defaultRole = "defaultRole")
public class RoleRequest {

	@JsonIgnore
	private Long roleUid;

	@NotNull
	@NotEmpty
	private String role;

	private Boolean defaultRole;

	@JsonSetter
	public void setDefaultRole(Boolean defaultRole) {
		this.defaultRole = (defaultRole != null) ? defaultRole : false;
	}

//	@JsonIgnore
//	private Integer deleteFlag;

	public RoleRequest() {
		  this.defaultRole = false;
	}

}
