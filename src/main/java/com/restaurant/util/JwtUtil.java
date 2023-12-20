package com.restaurant.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	public static final String SECRET = "";

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				   .setClaims(extraClaims)
				   .setSubject(userDetails.getUsername())
				   .setIssuedAt(new Date(System.currentTimeMillis()))
				   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				   .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode("HDFVCKJDBVJDBVJFDBVKJFDBVFDBVKFJHG7R98798734Y87HFC8ONO98Y4V98YTR74HO8VO948Y754VTG78EBVOEHIVH78V8754E745875486847UHVTGV8UYGRUTIE87587T56YGFFV8I4E75854");
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				   .setSigningKey(getSigningKey())
				   .build()
				   .parseClaimsJws(token)
				   .getBody();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	
}
