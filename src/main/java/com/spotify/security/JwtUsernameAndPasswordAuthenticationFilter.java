package com.spotify.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter   {

	private AuthenticationManager authManager;
	
	private final JwtConfig jwtConfig;
    
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig) {
		this.authManager = authManager;
		this.jwtConfig = jwtConfig;
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword(), Collections.emptyList());
			return authManager.authenticate(authToken);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		Long now = System.currentTimeMillis();
		List<String> s = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		String token = Jwts.builder()
				.setSubject(auth.getName())
				.claim("authorities", auth.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + jwtConfig.getExpiration() * 100000))  // in milliseconds
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
				.compact();
		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() +" "+ token);
		response.setStatus(200);
		response.addHeader("Access-Control-Expose-Headers", jwtConfig.getHeader());
		response.addHeader("roll",s.get(0));
	}

	private static class UserCredentials {
	    private String username, password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}