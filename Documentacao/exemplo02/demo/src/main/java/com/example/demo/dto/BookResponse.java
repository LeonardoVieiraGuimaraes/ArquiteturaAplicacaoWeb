package com.example.demo.dto;

import lombok.*;

/**
 * DTO de saída para Livro.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private Long authorId;
    private String authorName;
}
