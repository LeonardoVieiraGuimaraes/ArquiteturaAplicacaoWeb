package br.com.giulianabezerra.client_service_restclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("client")
class HelloController {
  private final RestClient restClient;

  public HelloController(RestClient restClient) {
    this.restClient = restClient;
  }

  @GetMapping("hello")
  public String hello() {
    return restClient.get()
        .uri("http://localhost:8000/hello")
        .retrieve()
        .body(String.class);
  }
}
