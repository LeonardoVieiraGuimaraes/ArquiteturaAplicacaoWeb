package br.com.giulianabezerra.client_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientOauthConfig {

  @Bean
  WebClient keycloakWebClientOauth(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2FilterFunction = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
        authorizedClientManager);
    oauth2FilterFunction.setDefaultClientRegistrationId("keycloak");

    return WebClient.builder()
        .filter(oauth2FilterFunction)
        .build();
  }
}
