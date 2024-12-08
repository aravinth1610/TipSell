package org.tipSell.handlerException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;
import org.tipSell.uniCore.customeResponse.ErrorResponse;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;
import org.tipSell.uniCore.securityConstant.SecurityMessages;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException {
		ResponseEntityWrapper<?> responseWrapper;	
		responseWrapper = new ResponseEntityWrapper<>("Failure",SecurityMessages.FORBIDDEN.message(),new ErrorResponse<>(SecurityMessages.FORBIDDEN.value(),HttpStatus.FORBIDDEN.getReasonPhrase(),request.getRequestURI(),null));
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, responseWrapper);
		outputStream.flush();
	}
}
