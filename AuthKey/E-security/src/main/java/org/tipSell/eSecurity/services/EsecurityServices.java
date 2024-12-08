package org.tipSell.eSecurity.services;

import java.util.Map;

import org.json.JSONObject;
import org.tipSell.eSecurity.payload.request.RegisterRequest;
import org.tipSell.uniCore.payload.request.CustomerRegisterRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface EsecurityServices {

	void customerRegister(RegisterRequest userRegister,String clientId);

	String generateSecureKey();

	String generateAuthenticationCode(long userUid,String clientId,String state, HttpSession session);

	JSONObject clientURIs(String realm, String clientId, HttpServletRequest request);

}
