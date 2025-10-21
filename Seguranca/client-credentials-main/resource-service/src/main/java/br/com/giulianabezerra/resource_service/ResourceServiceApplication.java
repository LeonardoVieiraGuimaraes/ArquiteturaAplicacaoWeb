package br.com.giulianabezerra.resource_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ResourceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServiceApplication.class, args);
	}

}

@RestController
@RequestMapping("hello")
class HelloController {
	@GetMapping
	public String hello(@AuthenticationPrincipal Jwt jwt) {
		return "Olá, Mundo!\n" + jwt.getTokenValue();
	}
}