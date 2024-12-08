package org.tipSell.uniCore.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRegisterRequest {

	@NotNull
	@Size(min = 4, max = 43)
	private String userName;
	// Need to see validGmail
	@NotNull
	private String gmail;
	// need to see pass and confir vali
	@NotNull
	@Size(min = 4, max = 43)
	private String password;
	@NotNull
	@Size(min = 4, max = 43)
	private String confirmPassword;

}
