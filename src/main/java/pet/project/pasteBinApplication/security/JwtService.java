package pet.project.pasteBinApplication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pet.project.pasteBinApplication.prop.JwtProperties;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.security.KeyRep.Type.SECRET;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties properties;

    public String generatedToken(UserDetails userDetails) { //метод генерации токена
        Map<String, String> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities().toString());
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(properties.getAccess())))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(properties.getSecret());
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt){
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt){
        Claims claims = getClaims(jwt);
        return  claims.getExpiration().after(Date.from(Instant.now()));
    }
}
