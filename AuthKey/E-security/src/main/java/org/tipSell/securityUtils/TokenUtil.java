package org.tipSell.securityUtils;

import static org.tipSell.authKey.Constant.AuthKeyConstant.TOKEN_ISSUER;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.tipSell.authKey.Constant.AuthKeyEnumConstant;
import org.tipSell.uniCore.securityConstant.SecurityConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

/**
 * aravinth 17-Jul-2024
 * 
 */
@Component
public class TokenUtil {


	private static String createToken(Map<String, Object> claims, String subject, String issuer,Long expTime,String unit) {
		JwtBuilder builder = Jwts.builder().setIssuer(issuer).setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()));
				
		 if (0 != expTime && null != unit  && !unit.isEmpty()) {
		        builder.setExpiration(calculateLifeSpan(expTime, unit));
		    }

		    return builder.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	private static Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(AuthKeyEnumConstant.TOKEN_SECRET_KEY.getValue());
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public static String tokenGeneratorSecretKey() {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String encodedKey = Encoders.BASE64.encode(key.getEncoded());
		return encodedKey;
	}

	public static String  getSubjectNameToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public static <T> Map<String, Object> getSpecificClaimFromToken(String token, Class<T> clazz, String... claimNames) {
		Claims claims = getAllClaimsFromToken(token);
		Map<String, Object> result = new HashMap<>();
		Iterator<String> iterator = Arrays.asList(claimNames).iterator();

		while (iterator.hasNext()) {
			String claimName = iterator.next();
			T claimValue = claims.get(claimName, clazz);
			result.put(claimName, claimValue);
		}

		return result;
	}

	public <T> T getSpecificClaimFromToken(String token, Class<T> clazz, String claimName) {
		Claims claims = getAllClaimsFromToken(token);
		return clazz.cast(claims.get(claimName));
	}

	private static Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	public static boolean isTokenValid(String token, String userName) {
		final String username = getSubjectNameToken(token);
		return (username.equals(userName) && !isTokenExpiredWithToken(token));
	}

	public static Date getExpirationDate(String token) {
		final Date expiration = getClaimFromToken(token, Claims::getExpiration);
		return expiration;
	}
	
	public static boolean isTokenExpiredWithDate(Date expirationDate) {
		final Date expiration = expirationDate;
		return (expiration.before(new Date()));
	}
	
	public static boolean isTokenExpiredWithToken(String token) {
		final Date expiration = getClaimFromToken(token, Claims::getExpiration);
		return (expiration.before(new Date()));
	}

	public Claims getClaimsFromToken(String token) {
		return getAllClaimsFromToken(token);
	}

	public SecretKey getJwtVerifier() {
		return (SecretKey) getSigningKey();
	}

//	public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
//		Claims claims = getAllClaimsFromToken(token);
//		String roles = claims.get(CryptokConstant.AUTHORITIES.getValue(), String.class);
//		return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//	}

	public static String generateToken(String userName, Map<String, Object> claims,long expTime,String unit) {
		return createToken(claims, userName, TOKEN_ISSUER, expTime, unit);
	}

	public static String generateRefreshToken(String userName, Map<String, Object> claims, long expTime,String unit) {
		return createToken(claims, userName, TOKEN_ISSUER, expTime, unit);
	}

    // Method to convert given time and unit (as a string) to milliseconds // Convert days to milliseconds
	  public static Date calculateLifeSpan(long time, String unit) {
	        long timeInMillis;
	        switch (unit.toLowerCase()) {
	            case "days":
	                timeInMillis = TimeUnit.DAYS.toMillis(time);     
	                break;
	            case "hours":
	                timeInMillis = TimeUnit.HOURS.toMillis(time);    
	                break;
	            case "minutes":
	                timeInMillis = TimeUnit.MINUTES.toMillis(time);  
	                break;
	            case "seconds":
	                timeInMillis = TimeUnit.SECONDS.toMillis(time);  
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid time unit: " + unit);
	        }
	        // Return a new Date representing the current time plus the calculated time in milliseconds
	        return new Date(System.currentTimeMillis() + timeInMillis);
	    }
	
}
