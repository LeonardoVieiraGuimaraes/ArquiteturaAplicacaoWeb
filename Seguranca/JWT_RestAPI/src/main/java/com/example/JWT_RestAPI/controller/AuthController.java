package com.example.JWT_RestAPI.controller;
import com.example.JWT_RestAPI.model.LoginRequest;
import com.example.JWT_RestAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
/**
 * Controlador responsável pelos endpoints de autenticação e demonstração de autorização.
 * <p>
 * - POST /login: recebe credenciais e devolve um token JWT (simulado).
 * - GET /username/{token}: extrai o username de um token JWT.
 * - GET /user: endpoint público que retorna o nome do usuário autenticado (Basic para exemplo).
 * - GET /admin: restrito ao perfil ADMIN.
 */
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * Realiza o "login" gerando um token (não há validação real de usuário/senha neste exemplo).
     * @param request corpo contendo username e password
     * @return token JWT como String
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        //Aqui você pode autenticar o usuário (por exemplo, usando um banco de dados)
        //Se a autenticação for bem-sucedida, gere um token JWT

        // String token = JwtUtil.generateToken(request.getUsername());
        // Ao invés de chamarmos JwtUtil diretamente, criamos uma camada de serviço
        String token = authService.generateToken(request.getUsername());
        return token;
    }
    // No login, capturamos o Username via corpo da requisição (LoginRequest Body)
    // Em seguida, geramos um token JWT

    /**
     * Extrai o username a partir de um token JWT informado na URL.
     * (Somente para fins didáticos; em produção prefira Bearer Token no header Authorization)
     * @param token token JWT
     * @return subject (username) contido no token
     */
    @GetMapping("/username/{token}")
    public String extractUsername(@PathVariable String token) {
        // String username = JwtUtil.extractUsername(token);
        // Ao invés de chamarmos JwtUtil diretamente, utilizamos a camada de serviço
        String username = authService.extractUsername(token);
        return username;
    }
    // No extractUsername, capturamos o token via URL apenas por praticidade
    // (poderia ser via @RequestBody também)
    // Em seguida, extraímos o username deste token

    // Qualquer um irá acessar
    /**
     * Endpoint aberto (permitAll na config) que retorna o nome do usuário autenticado.
     * @param authentication contexto de autenticação injetado pelo Spring Security
     * @return mensagem com o nome do usuário
     */
    @GetMapping("/user")
    public String getUser(Authentication authentication) {
        return "User: " + authentication.getName();
    }

    // Somente o admin irá acessar
    @Secured("ADMIN")
    /**
     * Endpoint restrito ao perfil ADMIN.
     * @param authentication contexto de autenticação injetado pelo Spring Security
     * @return mensagem com o nome do admin
     */
    @GetMapping("/admin")
    public String onlyAdmin(Authentication authentication) {
        return "Admin: " + authentication.getName();
    }
}
