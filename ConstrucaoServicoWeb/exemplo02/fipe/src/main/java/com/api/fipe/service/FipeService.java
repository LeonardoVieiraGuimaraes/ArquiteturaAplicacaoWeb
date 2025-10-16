package com.api.fipe.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FipeService {

    @Value("${fipe.api.base-url:https://parallelum.com.br/fipe/api/v1}")
    private String baseUrl;

    private String consultarURL(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return "Falha ao obter dados. Código de status: " + responseEntity.getStatusCode();
        }
    }

    public String consultarMarcas() {
        return consultarURL(baseUrl + "/carros/marcas");
    }

    public String consultarModelos(int id) {
        return consultarURL(baseUrl + "/carros/marcas/" + id + "/modelos");
    }

    public String consultarAnos(int marca, int modelo) {
        return consultarURL(baseUrl + "/carros/marcas/" + marca + "/modelos/" + modelo + "/anos");
    }

    public String consultarValor(int marca, int modelo, String ano) {
        return consultarURL(baseUrl + "/carros/marcas/" + marca + "/modelos/" + modelo + "/anos/" + ano);
    }
}
