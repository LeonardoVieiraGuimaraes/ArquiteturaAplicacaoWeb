package com.example.JWT_RestAPI.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utilitário JWT baseado na versão 0.12.3 do JJWT.
 * <p>
 * Observações importantes de segurança:
 * - Em produção, a chave secreta deve ser fixa, forte e protegida (ex.: variáveis de ambiente
 *   ou Secret Manager). Aqui, a chave é gerada em runtime apenas para fins didáticos.
 * - Tokens gerados com uma SECRET_KEY diferente não serão válidos após reiniciar a aplicação.
 */
public class JwtUtil {
    // É comum guardar essa SECRET_KEY separadamente em um arquivo de configuração
    private static final SecretKey SECRET_KEY = generateSecretKey();
    private static final String SECRET_STRING = getSecretString();
    private static final long EXPIRATION_TIME = 864_000_000;
    // 10 dias de validade para o token

    /**
     * Gera uma chave secreta HS512.
     * Em produção, prefira carregar de configuração segura.
     */
    private static SecretKey generateSecretKey() {
        SecretKey key = Jwts.SIG.HS512.key().build();
        return key;
    }

    /**
     * Codifica a chave secreta em Base64 para uso no parser.
     */
    private static String getSecretString() {
        String secretString =  Encoders.BASE64.encode(SECRET_KEY.getEncoded());
        System.out.println("Secret Key: " + secretString);
        return secretString;
    }
    
    /**
     * Gera um token JWT simples com subject (username) e expiração.
     * @param username identificador do usuário
     * @return token JWT assinado (compact)
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
     * Extrai o subject (username) a partir de um token JWT válido.
     * @param token token JWT assinado
     * @return subject contido no token
     * @throws io.jsonwebtoken.security.SecurityException se a assinatura for inválida
     * @throws io.jsonwebtoken.ExpiredJwtException se o token estiver expirado
     */
    public static String extractUsername(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_STRING));
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).
                getPayload().getSubject();
    }
}
