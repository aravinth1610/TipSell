package org.tipSell.eSecurity.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttributeRequest {

	@JsonIgnore
	private Long attributeUid;

	@NotNull
	@NotEmpty
	private String key;

	@NotNull
	@NotEmpty	
	private String value;

}
