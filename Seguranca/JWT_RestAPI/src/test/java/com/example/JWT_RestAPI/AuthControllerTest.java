package com.example.JWT_RestAPI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /login deve retornar um token não vazio")
    void login_deveRetornarToken() throws Exception {
        String body = "{\"username\":\"joao\",\"password\":\"4321\"}";
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyString())));
    }

    @Test
    @DisplayName("Fluxo: login -> username/{token} deve retornar o mesmo username")
    void fluxo_login_e_extractUsername() throws Exception {
        String body = "{\"username\":\"joao\",\"password\":\"4321\"}";
        String token = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(get("/username/{token}", token))
                .andExpect(status().isOk())
                .andExpect(content().string("joao"));
    }

    @Test
    @DisplayName("GET /admin sem credenciais deve ser 401/403 e com admin deve ser 200")
    void admin_endpoint_requires_admin_role() throws Exception {
        // não autenticado -> 401 (ou 403 dependendo da config)
        mockMvc.perform(get("/admin"))
                .andExpect(status().is4xxClientError());

        // autenticado com admin:1234 (definido em SecurityConfig) -> 200
        mockMvc.perform(get("/admin").with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic("admin", "1234")))
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("Admin: ")));
    }
}
