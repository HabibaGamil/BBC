package com.springboot.firstapp.Config;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${application.security.jwt.secret-key}")
	private String SECRET_KEY;
	
	@Value("${application.security.jwt.access-token.expiration}")
	private long accessTokenExpiration;
	
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshTokenExpiration;
	

	/**************************Extract all what you need from token*********************************/
	
	private Claims extractAllClaims(String token) {
		return
				Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public <T> T extractSpecificClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractUserName(String token) {
		   return extractSpecificClaim(token, Claims::getSubject);
	}
	
	public java.util.Date getExpirationTime(String token) {
		return extractSpecificClaim(token, Claims::getExpiration);
	}
	
	
	
	/**************************generate a token*************************************/

	
//	public String generateToken(UserDetails userDetails) {
//		return generateAccessToken(new HashMap<>() ,userDetails);
//	}
	
	public String generateAccessToken(UserDetails userDetails) {
		return buildCustomToken(new HashMap<>(),userDetails,accessTokenExpiration);
	}
	
	public String generateRefreshToken(UserDetails userDetails) {
		return buildCustomToken(new HashMap<>(),userDetails,refreshTokenExpiration);
	}
	
	public String buildCustomToken(Map<String, Object> extraClaims, 
								   UserDetails userDetails,
								   long expiration) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))//expiration data is after an hour, 1000*60*60
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	/**************************validate a token***************************************/
	
	public boolean isValidToken(String token, UserDetails userDetails) {
		return extractUserName(token).equals(userDetails.getUsername()) && ! isTokenExpired(token); 
	}
	
	public boolean isTokenExpired(String token) {
		try {
			return getExpirationTime(token).before(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			return true;
		}
	}
	
 }
