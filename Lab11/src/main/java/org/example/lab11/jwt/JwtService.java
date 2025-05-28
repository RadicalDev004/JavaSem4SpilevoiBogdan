package org.example.lab11.jwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "a";
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static String validateTokenAndGetUsername(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (Exception e){
            return "invalid";
        }
    }
}