package com.example.demo.services;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getGreeting(String name) {
        return String.format("Hello %s!", name);
    }
}
