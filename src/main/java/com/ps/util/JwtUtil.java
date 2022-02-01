package com.ps.util;

import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ps.serviceImpl.UserDetailsInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	
	private int refreshExpirationDateInMs;
	
	
	static final String CALM_KEY_USERNAME="sub";
	static final String CALM_KEY_CREATED="created";
	static final String CALM_KEY_AUDIENCE="audience";
	
	@Value("${app.secret}")
	private String secret;

	//7. validate token user name and request user also expDate
	public boolean validateToken(String token, UserDetails userDetails) {
//		String usernameInToken = getUsername(token);
		UserDetailsInfo user=(UserDetailsInfo )userDetails;
		final String username=getUsername(token);
		return (username.equals(username) && !isTokenExpired(token));
	}

	
	//6. Check Current and Exp Date
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpDate(token);
		return expiration.before(new Date());
	}
	
	
	@Value("${jwt.refreshExpirationDateInMs}")
	public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
		this.refreshExpirationDateInMs = refreshExpirationDateInMs;
	}
	
	

	//5. Generate Token with Empty Claims
	public String generateToken(UserDetailsInfo  userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		claims.put(CALM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CALM_KEY_CREATED, new Date());
		return generateToken(claims);
	}
	

	//4.Read username
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	//3. read ExpDate
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	//2. Read Claim
	private Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	//1. generate token	
	private String generateToken(Map<String, Object> claims) {

		return Jwts.builder()
				.setClaims(claims)
				.setIssuer("Prospecta")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}	
	
	
	
	
	public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() 
				+ refreshExpirationDateInMs))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();

	}
}





























