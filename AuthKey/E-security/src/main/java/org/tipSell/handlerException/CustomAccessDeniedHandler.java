package org.tipSell.handlerException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.tipSell.uniCore.customeResponse.ErrorResponse;
import org.tipSell.uniCore.customeResponse.ResponseEntityWrapper;
import org.tipSell.uniCore.securityConstant.SecurityConstant;
import org.tipSell.uniCore.securityConstant.SecurityMessages;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
			throws IOException, ServletException {
		ResponseEntityWrapper<?> responseWrapper;	
		responseWrapper = new ResponseEntityWrapper<>("Failure",SecurityMessages.UNAUTHORIZED.message(),new ErrorResponse<>(SecurityMessages.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),request.getRequestURI(),null));
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, responseWrapper);
		outputStream.flush();
	}

}
