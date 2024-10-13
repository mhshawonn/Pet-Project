package com.example.demo.Config;

import com.example.demo.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Handler;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final HandlerExceptionResolver handlerExceptionResolver;
	
	private final JwtService jwtService;
	
	private final UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(
			JwtService jwtService,
			UserDetailsService userDetailsService,
			HandlerExceptionResolver handlerExceptionResolver
	) {
		this.handlerExceptionResolver = handlerExceptionResolver;
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}
	
	
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain
			) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		
		System.out.println("authHeader : " + authHeader);
		
//		Enumeration<String> headerNames = request.getHeaderNames();
//
//		while (headerNames.hasMoreElements()) {
//			String headerName = headerNames.nextElement();
//			String headerValue = request.getHeader(headerName);
//			System.out.println(headerName + ": " + headerValue);
//		}
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")){
			filterChain.doFilter(request, response);
			return;
		}
		
		try{
			final String jwt = authHeader.substring(7);
			final String userEmail = jwtService.getEmailFromToken(jwt);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			System.out.println("userEmail : " + userEmail);
			System.out.println("authentication : " + authentication);
			
			if(userEmail != null && authentication == null){
				System.out.println("hello");
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
				System.out.println("hello");
				System.out.println("userDetails : " + userDetails);
				
				if(jwtService.isTokenValid(jwt,userDetails)){
					
					System.out.println("valid token");
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
					System.out.println("authToken : " + authToken);
				}
			}
			
			
			filterChain.doFilter(request, response);
		} catch (Exception e){
			e.printStackTrace();
			handlerExceptionResolver.resolveException(request, response, null, e);
		}
	}
}
