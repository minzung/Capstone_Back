package hansung.capstone.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtil {

    private String secretKey = "thisIsASuperSecretKeyForJwtSigningAndVerification12345678";
    private long accessValidityInMilliseconds = 3600000L; // 1 hour
    private long refreshValidityInMilliseconds = 86400000L * 30; // 30 day

    public String createAccessToken(String studentId) {
        Claims claims = Jwts.claims().setSubject(studentId);

        Date now = new Date();
        Date validity = new Date(now.getTime() + accessValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String studentId) {
        Claims claims = Jwts.claims().setSubject(studentId);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
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
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
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
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

}
