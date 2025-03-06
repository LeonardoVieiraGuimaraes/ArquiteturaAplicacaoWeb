package com.av.first.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// Anotação que indica que esta classe é um serviço do Spring
@Service
public class FipeService {

    // Instância de RestTemplate para fazer requisições HTTP
    private final RestTemplate restTemplate = new RestTemplate();

    // Método privado que faz a requisição HTTP para a URL fornecida e retorna a resposta como String
    private String consultarURL(String apiUrl) {
        // Faz a requisição GET para a URL fornecida e obtém a resposta como String
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        // Verifica se a resposta tem um status 2xx (sucesso)
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            // Retorna o corpo da resposta
            return responseEntity.getBody();
        } else {
            // Retorna uma mensagem de erro com o código de status
            return "Falha ao obter dados. Código de status: " + responseEntity.getStatusCode();
        }
    }

    // Método público que consulta as marcas de carros na API FIPE
    public String consultarMarcas() {
        // URL da API para consultar marcas
        String apiUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
        // Chama o método consultarURL com a URL da API e retorna a resposta
        return consultarURL(apiUrl);
    }

    // Método público que consulta os modelos de uma marca específica na API FIPE
    public String consultarModelos(int marca) {
        // URL da API para consultar modelos de uma marca específica
        String apiUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" + marca + "/modelos";
        // Chama o método consultarURL com a URL da API e retorna a resposta
        return consultarURL(apiUrl);
    }

    // Método público que consulta os anos de um modelo específico de uma marca específica na API FIPE
    public String consultarAnos(int marca, int modelo) {
        // URL da API para consultar anos de um modelo específico de uma marca específica
        String apiUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" + marca + "/modelos/" + modelo + "/anos";
        // Chama o método consultarURL com a URL da API e retorna a resposta
        return consultarURL(apiUrl);
    }

    // Método público que consulta o valor de um modelo específico de uma marca específica em um ano específico na API FIPE
    public String consultarValor(int marca, int modelo, String ano) {
        // URL da API para consultar o valor de um modelo específico de uma marca específica em um ano específico
        String apiUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas/" + marca + "/modelos/" + modelo + "/anos/" + ano;
        // Chama o método consultarURL com a URL da API e retorna a resposta
        return consultarURL(apiUrl);
    }
}