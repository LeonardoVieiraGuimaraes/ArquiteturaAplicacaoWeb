package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username e senha são obrigatórios.");
        }
        Optional<User> userOpt = userService.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()));
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
            List<String> roles = user.getRoles().stream()
                    .map(r -> r.getName())
                    .collect(Collectors.toList());

            Date now = new Date();
            Date exp = new Date(now.getTime() + EXPIRATION_MILLIS);

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .claim("scope", roles)
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
