package com.example.democrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.democrud.service.HelloService;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloService helloservice;

    @GetMapping
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return helloservice.getHello(name);
    }
    
}
