package org.tipSell.grantTypeFactory;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract interface GrantTypeFactory {
	  void validate(HttpServletResponse response, Map<?,?> claims) throws Exception;

}
