package org.tipSell.securityFilters;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tipSell.authKey.Constant.AuthKeyConstant;
import org.tipSell.grantTypeFactory.GrantTypeFactory;
import org.tipSell.grantTypeFactoryImpl.GrantTypeValidatorFactory;
import org.tipSell.securityUtils.TokenUtil;
import org.tipSell.uniCore.customeExceptions.UnauthorizedException;
import org.tipSell.uniCore.securityConstant.SecurityConstant;
import org.tipSell.uniCore.securityConstant.SecurityMessages;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

	private final GrantTypeValidatorFactory grantTypeValidatorFactory;

	private String grantTypeKey = AuthKeyConstant.CLAIM_GRANTYPE;
	private String authoritiesKey = AuthKeyConstant.AUTHORITIES;
	private String profilesKey = AuthKeyConstant.CLAIM_PROFILES;
	private String subjectKey = AuthKeyConstant.CLAIM_SUB;
	private String expirationKey = AuthKeyConstant.CLAIM_EXPIRATION;

	public TokenValidationFilter(GrantTypeValidatorFactory grantTypeValidatorFactory) {
		super();
		this.grantTypeValidatorFactory = grantTypeValidatorFactory;
	}

//	private final TokenUtils tokenUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getMethod().equalsIgnoreCase(AuthKeyConstant.OPTIONS_HTTP_METHOD)) {
			response.setStatus(HttpStatus.OK.value());
			return;
		}

		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token == null || !StringUtils.startsWithIgnoreCase(token.trim(), AuthKeyConstant.TOKEN_PREFIX)) {
			throw new UnauthorizedException("Unauthorized access - invalid token");
		}

		token = token.substring(AuthKeyConstant.TOKEN_PREFIX.length()).trim();
		System.out.println(token);
		try {

			Map<?, ?> claims = TokenUtil.getSpecificClaimFromToken(token, Object.class, grantTypeKey, authoritiesKey, profilesKey, subjectKey, expirationKey);
			String grantType = claims.get(grantTypeKey).toString();
			
			
			
//		String subject = TokenUtil.getSubjectNameToken(token);
//			Date expirationDate = TokenUtil.getExpirationDate(token);
//			boolean isTokenValid = TokenUtil.isTokenValid(token, subject);
//			if (isTokenValid) {

//				Map<String, Object> claims = TokenUtil.getSpecificClaimFromToken(token, Object.class, getGrantType,getAuthorities, getProfiles,"sub","Exp");
//
//				String subject = claims.get("sub").toString();
//				String grantType = claims.get(getGrantType).toString();
//				String authority = claims.get(getAuthorities).toString();
//				Object profiles = claims.get(getProfiles);
//
//				if (null == subject) {
//					throw new AccessDeniedException("Invalid Subject");
//				}

				GrantTypeFactory validator = grantTypeValidatorFactory.getValidator(grantType);

				if (validator != null) {
					validator.validate(response, claims);
				}
//			} else {
//				SecurityContextHolder.clearContext();
//			}
		} catch (Exception e) {
			SecurityContextHolder.clearContext();
			throw new UnauthorizedException(SecurityMessages.INVALID_TOKEN.message());
		}

		filterChain.doFilter(request, response);

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return AuthKeyConstant.SHOULDNOTFILTERVALIDATOR.stream().map(uri -> uri.replace("/**", ""))
				.anyMatch(uri -> request.getServletPath().contains(uri));
	}
}