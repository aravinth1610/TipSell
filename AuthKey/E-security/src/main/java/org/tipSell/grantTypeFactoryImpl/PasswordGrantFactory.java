package org.tipSell.grantTypeFactoryImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.tipSell.authKey.Constant.AuthKeyConstant;
import org.tipSell.grantTypeFactory.GrantTypeFactory;
import org.tipSell.securityUtils.TokenUtil;
import org.tipSell.uniCore.customeExceptions.UnauthorizedException;
import org.tipSell.uniCore.securityConstant.SecurityMessages;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class PasswordGrantFactory implements GrantTypeFactory {

	@Override
	public void validate(HttpServletResponse response, Map<?, ?> claims)
			throws Exception {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
		String subject = claims.get(AuthKeyConstant.CLAIM_SUB).toString();
        String authority = claims.get(AuthKeyConstant.AUTHORITIES).toString();
        String expirationDateString = claims.get(AuthKeyConstant.CLAIM_EXPIRATION).toString();
        String profile = Optional.ofNullable(claims.get(AuthKeyConstant.CLAIM_PROFILES)).map(Object::toString).orElse("");
		
	    Date expirationDate = dateFormat.parse(expirationDateString);
	    
		boolean isTokenValid = TokenUtil.isTokenExpiredWithDate(expirationDate);		
		if (isTokenValid) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(subject , null,
					AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
//			response.setHeader("X-User-ID", "subject");
		}else {
			SecurityContextHolder.clearContext();
			throw new UnauthorizedException(SecurityMessages.INVALID_TOKEN.message());
		}
	}

}
