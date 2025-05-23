package com.attendance.system.attendance_server.security;

        import io.jsonwebtoken.*;
        import io.jsonwebtoken.io.Decoders;
        import io.jsonwebtoken.security.Keys;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Component;

        import javax.crypto.SecretKey;
        import java.nio.charset.StandardCharsets;
        import java.util.Date;

@Component
public class JwtUtil {
//    private final String SECRET = "replace-with-a-strong-secret-skjdfdsnfsdfjewnfdjksnfnkjfdnsfsndfnkjsfwfnskdndsnkjfdsnfkjsdnfnfkjsdvfddgdgdgfg-dfrdsfdsgdsgdgdfgs-rfgsdgsv";;
//    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


        private final SecretKey key;

        public JwtUtil(@Value("${jwt.secret-base64}") String base64Key) {
            byte[] decoded = Decoders.BASE64.decode(base64Key);
            this.key = Keys.hmacShaKeyFor(decoded);
        }

    // 1 hour expiration
    private final long EXPIRATION_MS = 3600_000;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
