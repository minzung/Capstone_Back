package hansung.capstone.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = generateSecretKey(); // 시크릿 키
    private static final int SECRET_KEY_LENGTH = 32; // 256비트, 32바이트
    private long ACCESS_EXPIRATION_TIME = 3600000L; // 1 hour
    private long REFRESH_EXPIRATION_TIME = 86400000L * 30; // 30 day

    public String createAccessToken(String studentId) {
        Claims claims = Jwts.claims().setSubject(studentId);
        claims.put("aud", studentId);
        if(studentId.equals("2071243")) {
            claims.put("roles", "admin");
        } else {
            claims.put("roles", "user");
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + ACCESS_EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String createRefreshToken(String studentId) {
        Claims claims = Jwts.claims().setSubject(studentId);
        claims.put("aud", studentId);
        if(studentId.equals("2071243")) {
            claims.put("roles", "admin");
        } else {
            claims.put("roles", "user");
        }

        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String refreshToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            return null;
        }

        String studentId = getStudentIdFromToken(refreshToken);
        return createAccessToken(studentId);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isAuthenticated(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token == null) {
            return false;
        }
        return validateToken(token);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getStudentIdFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    // 무작위 문자열로 SECRET_KEY 생성
    private static String generateSecretKey() {
        byte[] randomBytes = new byte[SECRET_KEY_LENGTH];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
