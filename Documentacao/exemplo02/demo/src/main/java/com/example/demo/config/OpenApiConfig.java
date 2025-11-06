package com.example.demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger/OpenAPI (springdoc) com informações customizadas
 * exibidas no Swagger UI (\n/swagger-ui.html).
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define o objeto OpenAPI com metadados: título, descrição, versão, contato e licença.
     */
    @Bean
    public OpenAPI demoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo API - Autores e Livros")
                        .description("Exemplo simples de CRUD com relacionamento 1:N (Autor -> Livro) usando Spring Boot, JPA/H2, DTOs, validação e Swagger.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Equipe Demo")
                                .email("demo@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação do projeto no GitHub")
                        .url("https://github.com/LeonardoVieiraGuimaraes/ArquiteturaAplicacaoWeb"));
    }
}
