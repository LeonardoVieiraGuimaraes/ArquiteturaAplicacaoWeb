package br.com.giulianabezerra.client_service_restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientOauthConfig {

  @Bean
  RestClient keycloakRestClientOauth(RestClient.Builder builder,
      OAuth2AuthorizedClientManager authorizedClientManager) {
    OAuth2ClientHttpRequestInterceptor requestInterceptor = new OAuth2ClientHttpRequestInterceptor(
        authorizedClientManager);
    requestInterceptor.setClientRegistrationIdResolver((HttpRequest request) -> "keycloak");

    return builder
        .requestInterceptor(requestInterceptor)
        .build();
  }
}
