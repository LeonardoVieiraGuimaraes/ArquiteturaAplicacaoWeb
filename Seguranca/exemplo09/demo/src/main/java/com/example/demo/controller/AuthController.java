package com.example.demo.controller;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String secretKey;

    private static final long EXPIRATION_MILLIS = 1000 * 60 * 60 * 4; // 4 horas

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        System.out.println("Tentando login para: " + request.getUsername());
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username e senha são obrigatórios.");
        }
        Optional<User> userOpt = userService.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()));
        System.out.println("Usuário encontrado? " + userOpt.isPresent());
        if (userOpt.isPresent()) {
            return generateTokenResponse(userOpt.get());
        }
        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }

    private ResponseEntity<?> generateTokenResponse(User user) {
        try {
            if (secretKey.getBytes(StandardCharsets.UTF_8).length < 32) {
                return ResponseEntity.status(500).body("A chave secreta JWT precisa ter pelo menos 32 bytes (256 bits)");
            }
            // Troque para string separada por espaço
            String scope = user.getRoles().stream()
                    .map(r -> r.getName())
                    .collect(Collectors.joining(" "));

            Date now = new Date();
            Date exp = new Date(now.getTime() + EXPIRATION_MILLIS);

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .claim("scope", scope)
                    .issueTime(now)
                    .expirationTime(exp)
                    .build();

            JWSSigner signer = new MACSigner(secretKey.getBytes(StandardCharsets.UTF_8));
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claims
            );
            signedJWT.sign(signer);

            String token = signedJWT.serialize();
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (JOSEException e) {
            return ResponseEntity.status(500).body("Erro ao gerar token JWT");
        }
    }

    @Data
    public static class AuthRequest {

        private String username;
        private String password;
    }

    @Data
    public static class AuthResponse {

        private final String token;
    }
}
