package com.example.JWT_RestAPI.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utilitário para geração e extração de tokens JWT.
 */
public class JwtUtil {
    private static final SecretKey SECRET_KEY = generateSecretKey();
    private static final String SECRET_STRING = getSecretString();
    private static final long EXPIRATION_TIME = 864_000_000; // 10 dias de validade para o token

    private static SecretKey generateSecretKey() {
        SecretKey key = Jwts.SIG.HS512.key().build();
        return key;
    }

    private static String getSecretString() {
        String secretString = Encoders.BASE64.encode(SECRET_KEY.getEncoded());
        System.out.println("Secret Key: " + secretString);
        return secretString;
    }

    /**
     * Gera um token JWT para o nome de usuário fornecido.
     *
     * @param username o nome de usuário para o qual o token será gerado.
     * @return o token JWT gerado.
     */
    public static String generateToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        System.out.println("Token: " + token);
        return token;
    }

    /**
     * Extrai o nome de usuário de um token JWT fornecido.
     *
     * @param token o token JWT do qual o nome de usuário será extraído.
     * @return o nome de usuário extraído do token.
     */
    public static String extractUsername(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
}