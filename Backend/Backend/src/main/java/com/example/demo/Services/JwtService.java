package com.example.demo.Services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
	
	@Value("${security.jwt.secret-key}")
	private String secretKey;
	
	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;
	
	public String extractUsername( String token ) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public String getEmailFromToken( String token ) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim( String token, Function< Claims, T> claimsResolver ) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken( UserDetails userDetails ){
		
		return generateToken( new HashMap<>(), userDetails);
	}
	
	public String generateToken( Map<String, Object > extractClaims, UserDetails userDetails){
		return buildToken(extractClaims, userDetails, jwtExpiration);
	}
	
	public Long getExpirationTime(){
		return jwtExpiration;
	}
	
	
	private String buildToken(Map<String, Object> extractClaims, UserDetails userDetails,long expiration){
		return Jwts
				.builder()
				.setClaims(extractClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(new Date().getTime() + 864000000 ))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid( String token, UserDetails userDetails ){
		final String username = extractUsername(token);
		return (Objects.equals(username, userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired( String token ){
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration( String token ){
		return extractClaim(token, Claims::getExpiration);
	}
	
	private Claims extractAllClaims( String token ){
		
		System.out.println("claim token : " + token);
		
		if(token.startsWith("Bearer ")){
			token = token.substring(7);
		}
		
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSignInKey(){
		byte[]  keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
