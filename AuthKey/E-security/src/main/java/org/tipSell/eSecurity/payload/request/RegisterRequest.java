package org.tipSell.eSecurity.payload.request;

import org.springframework.beans.factory.annotation.Value;
import org.tipSell.custom.annotation.IsUserRegister;
import org.tipSell.uniCore.payload.request.CustomerRegisterRequest;
import org.tipSell.uniCore.securityConstant.SecurityConstant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@IsUserRegister(mailField = "mail",password="password",confirmPassword="confirmPassword")
public class RegisterRequest {

	
	@NotNull
	@Size(min = 4, max = 43)
	private String userName;
	
	@NotNull
	private String mail;

	@NotNull
	@Size(min = 4, max = 43)
	private String password;
	
	@NotNull
	@Size(min = 4, max = 43)
	private String confirmPassword;

	
}

//@Builder
//public record userRegisterRequest(@NotBlank @NotNull @Size(min = 4, max = 43) String userName,
//		@NotBlank @NotNull @Pattern(regexp = SecurityConstant.EMAIL_PATTERN) String gmail,
//		@NotNull @NotBlank @Size(min = 4, max = 43) String password, @NotNull @NotBlank @Size(min = 4, max = 43) String confirmPassword) {
//}