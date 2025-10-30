package com.example.demo.pedido;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração dos endpoints de Pedido e verificação básica dos endpoints de Actuator.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void criaPedido_comSucesso() throws Exception {
    String body = "{\"valor\": 123.45, \"usuario\": \"alice\"}";
    mockMvc.perform(post("/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.valor").value(123.45))
        .andExpect(jsonPath("$.usuario").value("alice"));
    }

    @Test
    void criaPedido_invalido_retorna400() throws Exception {
    // valor inválido (0) e usuario vazio
    String body = "{\"valor\": 0, \"usuario\": \"\"}";
    mockMvc.perform(post("/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isBadRequest());
    }

    @Test
    void obterPedido_existente_e_naoEncontrado() throws Exception {
    // cria um pedido válido
    String body = "{\"valor\": 50.0, \"usuario\": \"bob\"}";
    String response = mockMvc.perform(post("/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();

    // extrai ID de forma simples
    String id = response.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

    // busca o existente
    mockMvc.perform(get("/pedidos/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id));

    // busca um inexistente
    mockMvc.perform(get("/pedidos/" + UUID.randomUUID()))
        .andExpect(status().isNotFound());
    }

    @Test
    void listaPedidos_retornaColecao() throws Exception {
    // cria alguns pedidos
    mockMvc.perform(post("/pedidos").contentType(MediaType.APPLICATION_JSON)
        .content("{\"valor\": 10.0, \"usuario\": \"u1\"}"))
        .andExpect(status().isCreated());
    mockMvc.perform(post("/pedidos").contentType(MediaType.APPLICATION_JSON)
        .content("{\"valor\": 20.0, \"usuario\": \"u2\"}"))
        .andExpect(status().isCreated());

    mockMvc.perform(get("/pedidos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(Matchers.greaterThanOrEqualTo(2))));
    }

    @Test
    void actuator_health_e_metrics_disponiveis() throws Exception {
    // health deve estar UP
    mockMvc.perform(get("/actuator/health"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("UP"));

    // cria ao menos um pedido para garantir métrica de negócio
    mockMvc.perform(post("/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"valor\": 15.5, \"usuario\": \"carol\"}"))
        .andExpect(status().isCreated());

    // endpoint de métricas JSON deve expor nossa métrica de negócio
    mockMvc.perform(get("/actuator/metrics/business_pedidos_criados_total"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("business_pedidos_criados_total"))
        .andExpect(jsonPath("$.measurements").isArray());
    }
}
