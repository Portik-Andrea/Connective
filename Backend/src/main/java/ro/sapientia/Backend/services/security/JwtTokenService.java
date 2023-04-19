package ro.sapientia.Backend.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;



@Slf4j
@Service
public class JwtTokenService {
    private final long jwtTokenValidity;
    private final Algorithm hmac512;
    private final JWTVerifier verifier;
    public JwtTokenService(
            @Value("${jwt.secret}") final String secret,
            @Value("${jwt.token.expiration.millis}") final long jwtTokenValidity) {
        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
        this.jwtTokenValidity = jwtTokenValidity;
    }
    public String generateToken(final UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtTokenValidity))
                .sign(this.hmac512);

    }
    public String validateTokenAndGetUsername(final String token) {
        try {
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            log.warn("token invalid: {}", verificationEx.getMessage());
            return null;
        }
    }
}
