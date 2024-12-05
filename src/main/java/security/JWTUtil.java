// src/main/java/security/JWTUtil.java
package security;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
	private static final long EXPIRATION_TIME = 86400000; // 24時間

	public static String generateToken(String userId) {
		return Jwts.builder()
				.setSubject(userId)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SecurityConfig.getJwtKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public static Claims validateToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SecurityConfig.getJwtKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}