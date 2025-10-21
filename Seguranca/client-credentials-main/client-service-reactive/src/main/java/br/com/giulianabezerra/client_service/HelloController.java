package br.com.giulianabezerra.client_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("client")
class HelloController {
  private final WebClient webClient;

  public HelloController(WebClient webClient) {
    this.webClient = webClient;
  }

  @GetMapping("hello")
  public Mono<String> hello() {
    return webClient
        .get()
        .uri("http://localhost:8000/hello")
        .retrieve()
        .bodyToMono(String.class);
  }

}
