package com.example.demo.dto;

import lombok.*;

/**
 * DTO de saída para Autor.
 * - O que retornamos nas respostas da API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponse {
    private Long id;
    private String name;
}
