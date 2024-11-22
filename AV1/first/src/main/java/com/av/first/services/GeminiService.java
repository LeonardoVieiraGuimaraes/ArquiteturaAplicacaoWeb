package com.av.first.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// Anotação que indica que esta classe é um serviço do Spring
@Service
public class GeminiService {

    // Instância de RestTemplate para fazer requisições HTTP
    private final RestTemplate restTemplate = new RestTemplate();

    // Método público que gera conteúdo usando a API do Gemini
    public String generateContent() {
        // URL da API do Gemini com a chave de API
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyAcm29YUy6EI9mZT8Rxe3BmDc28WoGtG-Q";
        
        // JSON de requisição que será enviado para a API
        String requestJson = "{\"contents\":[{\"parts\":[{\"text\":\"O que Inteligencia Artificial\"}]}]}";
        
        // Configura os headers da requisição, definindo o tipo de conteúdo como JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Cria a entidade HTTP que inclui o JSON de requisição e os headers
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        
        // Faz a requisição POST para a API do Gemini e obtém a resposta
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
        
        // Verifica se a resposta tem um status 2xx (sucesso)
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                // Cria um ObjectMapper para processar a resposta JSON
                ObjectMapper mapper = new ObjectMapper();
                // Lê a resposta JSON e a converte em um JsonNode
                JsonNode root = mapper.readTree(responseEntity.getBody());
                // Extrai o texto da resposta JSON e o retorna
                return root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
            } catch (JsonProcessingException e) {
                // Retorna uma mensagem de erro caso ocorra uma exceção ao processar a resposta
                return "Erro ao processar a resposta: " + e.getMessage();
            }
        } else {
            // Retorna uma mensagem de erro com o código de status caso a resposta não seja bem-sucedida
            return "Falha ao obter dados. Código de status: " + responseEntity.getStatusCode();
        }
    }
}