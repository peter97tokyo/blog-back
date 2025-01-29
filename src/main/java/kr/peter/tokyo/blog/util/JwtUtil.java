package kr.peter.tokyo.blog.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 비밀키 (반드시 안전하게 보관)
    private final long EXPIRATION_TIME = 500 * 60 * 60; // 1시간

    // 1. 토큰 생성
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // 사용자 역할(role) 저장
        return Jwts.builder()
                .setClaims(claims) // 사용자 데이터 추가
                .setSubject(username) // 사용자 이름 저장
                .setIssuedAt(new Date()) // 토큰 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(SECRET_KEY) // 서명 알고리즘 및 비밀키 설정
                .compact();
    }

    // 2. 토큰에서 클레임 추출
    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY) // 비밀키로 서명 검증
                    .build()
                    .parseClaimsJws(token) // 토큰 파싱
                    .getBody(); // 클레임 반환
        } catch (JwtException e) {
            throw new MalformedJwtException("Invalid JWT token", e);
        }
    }

    // 3. 토큰에서 사용자 이름 추출
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 4. 토큰에서 역할(Role) 추출
    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    // 5. 토큰 유효성 검증
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token); // 서명 검증
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 유효하지 않은 토큰
            return false;
        }
    }
}
