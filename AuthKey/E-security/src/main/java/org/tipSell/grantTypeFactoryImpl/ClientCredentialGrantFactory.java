package org.tipSell.grantTypeFactoryImpl;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.tipSell.authKey.Constant.AuthKeyConstant;
import org.tipSell.grantTypeFactory.GrantTypeFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ClientCredentialGrantFactory implements GrantTypeFactory {

	@Override
	public void validate(HttpServletResponse response, Map<?, ?> claims) throws Exception {

		String subject = claims.get(AuthKeyConstant.CLAIM_SUB).toString();
        String authority = claims.get(AuthKeyConstant.AUTHORITIES).toString();
		        
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(subject, null,
				                      AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
//			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}