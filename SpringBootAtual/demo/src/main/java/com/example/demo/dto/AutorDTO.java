// DTO para transferir dados do Autor
package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AutorDTO {
    private Long id;

    @NotBlank(message = "O nome do autor é obrigatório")
    @Size(max = 60, message = "O nome deve ter no máximo 60 caracteres")
    private String nome;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
