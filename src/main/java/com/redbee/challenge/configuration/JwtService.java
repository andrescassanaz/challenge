package com.redbee.challenge.configuration;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {

	private final static long EXPIRATION_TIME = 3600_000;
	private final static String SECRET = "REDBEE-CHALLENGE";
	private final static String HEADER = "Authorization";

	@Autowired
	UserDetailsService userDetails;

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

	public Authentication validateToken(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(HEADER);
		if (token != null) {
			String username;
			try {
				Claims jwt = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
				username = jwt.getSubject();
				token = generateToken(username);
				response.setHeader(HEADER, token);
			} catch (Exception e) {
				return null;
			}
			if (username != null) {
				UserDetails user = userDetails.loadUserByUsername(username);
				return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
						user.getAuthorities());
			}
		}
		return null;
	}

}
