package org.tipSell.uniCore.customeResponse;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder({ "status","message","data","errors" })
public class ResponseEntityWrapper<T> extends DataResponse<T> {

	@JsonProperty("status")
	private String status;
	@JsonProperty("errors")
	private ErrorResponse<?> error;
	@JsonProperty("message")
	private String message;

	public ResponseEntityWrapper(String status, T data, String message) {
		super();
		this.status = status;
		this.setData(data);
		this.message = message;
	}

	public ResponseEntityWrapper(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ResponseEntityWrapper(String status, String message, ErrorResponse<?> error) {
		super();
		this.status = status;
		this.message = message;
		this.error = error;
	}

}
