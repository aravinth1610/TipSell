package org.tipSell.uniCore.customeResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author aravinth
 * @since 2024
 *
 *        A sample source file for the code formatter preview
 */

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "errorCode", "reason", "schemaPath", "timestamp","validationErrors", "data" })
public class ErrorResponse<T> extends DataResponse<T> {

	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("reason")
	private String reason;
	@JsonProperty("schemaPath")
	private String schemaPath;
	@JsonProperty("validationErrors")
	private Map<String, ?> validationErrors;
	@JsonProperty("timestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy hh:mm:ss a", timezone = "Asia/Kolkata")
	private Date timestamp = new Date();

	public ErrorResponse(String errorCode, String reason, String schemaPath,Map<String, ?> validationErrors) {
		super();
		this.schemaPath = schemaPath;
		this.errorCode = errorCode;
		this.reason = reason;
		this.validationErrors=validationErrors;
	}
	
	public ErrorResponse(String errorCode, String reason, String schemaPath, T data) {
		super();
		this.errorCode = errorCode;
		this.reason = reason;
		this.schemaPath = schemaPath;
		this.setData(data);
	}

}
