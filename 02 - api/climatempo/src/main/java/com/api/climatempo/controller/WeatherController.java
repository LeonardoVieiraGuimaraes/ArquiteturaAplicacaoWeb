package com.api.climatempo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.climatempo.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController // Anotação que indica que esta classe é um controlador REST
@RequestMapping("/weather") // Mapeia as requisições que começam com "/weather" para esta classe
public class WeatherController {

    @Autowired // Injeta uma instância do WeatherService nesta classe
    private WeatherService weatherService;

    @GetMapping("/country/{country}") // Mapeia requisições GET para "/weather/country/{country}"
    public JsonNode getWeatherByCountry(@PathVariable String country) {
        // Chama o método getWeatherByCountry do WeatherService e retorna o resultado
        return weatherService.getWeatherByCountry(country);
    }
}





// package com.api.climatempo.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.api.climatempo.model.WeatherData;
// import com.api.climatempo.service.WeatherService;

// @RestController // Anotação que indica que esta classe é um controlador REST
// @RequestMapping("/weather") // Mapeia as requisições que começam com "/weather" para esta classe
// public class WeatherController {

//     @Autowired // Injeta uma instância do WeatherService nesta classe
//     private WeatherService weatherService;

//     @GetMapping("/country/{country}") // Mapeia requisições GET para "/weather/country/{country}"
//     public WeatherData getWeatherByCountry(@PathVariable String country) {
//         // Chama o método getWeatherByCountry do WeatherService e retorna o resultado
//         return weatherService.getWeatherByCountry(country);
//     }
// }