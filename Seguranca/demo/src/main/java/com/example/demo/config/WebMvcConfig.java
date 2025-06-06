package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // Habilita o suporte ao Spring MVC
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        // Mapeia a URL "/login" diretamente para a página de login
        registry.addViewController("/login").setViewName("login");
        // Mapeia a URL "/logout-success" diretamente para uma página de logout bem-sucedido
        registry.addViewController("/logout-success").setViewName("logout-success");
        // Mapeia a URL "/index" para a página index.html
        registry.addViewController("/index").setViewName("index");
    }
}
